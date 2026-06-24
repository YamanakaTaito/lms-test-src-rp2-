package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
		//定数化
		final String EVIDENCE_DIR_PATH = "evidence\\";
		final String CHECK_TITLE = "ログイン | LMS";
		final String LOGIN_BUTTON_TEXT = "ログイン";
		final String EVIDENCE_FILE_NAME_BASE = "ログイン画面";

		WebDriverUtils.goTo("http://localhost:8080/lms");
		assertEquals(CHECK_TITLE, webDriver.getTitle());
		WebElement classSelecterBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		assertEquals(LOGIN_BUTTON_TEXT, classSelecterBtnElement.getAttribute("value"));
		//ログイン画面のスクリーンショットをとる
		Path path = Path.of(EVIDENCE_DIR_PATH);
		if (!Files.exists(path)) {
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		//定数化
		final String LOGIN_ID = "StudentAA01";
		final String PASSWORD = "StudentAA01";
		final String CHECK_TITLE = "コース詳細 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "コース詳細画面";

		//loginIdタグを選択して、指定の値を入力
		WebElement loginIdElement = webDriver.findElement(By.id("loginId"));
		loginIdElement.clear();
		loginIdElement.sendKeys(LOGIN_ID);
		//passwordタグを選択して、指定の値を入力
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys(PASSWORD);

		//入力後.btn.btn-primaryをCSSセレクターで選択して、click
		WebElement classSelecterBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		classSelecterBtnElement.click();
		//遷移後、ページ生成とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs(CHECK_TITLE));
		//ページ遷移したかタイトルで確認
		assertEquals(CHECK_TITLE, webDriver.getTitle());
		//ログイン後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		// TODO ここに追加
		//定数化
		final String LINK_TEXT = "勤怠";
		final String CHECK_TITLE = "勤怠情報変更｜LMS";
		final String EVIDENCE_FILE_NAME_BASE = "勤怠情報変更画面";
		//勤怠リンクをクリックする
		webDriver.findElement(By.linkText(LINK_TEXT)).click();
		//アラートのポップアップ画面処理
		webDriver.switchTo().alert().accept();
		//ページ遷移したかタイトルで確認
		assertEquals(CHECK_TITLE, webDriver.getTitle());
		//ページ遷移後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		// TODO ここに追加
		//定数化
		final String LINK_TEXT = "勤怠情報を直接編集する";
		final String CHECK_URL = "lms/attendance/update";
		final String EVIDENCE_FILE_NAME_BASE = "勤怠情報を直接編集する画面";

		//勤怠情報を直接編集するリンクをクリックする
		webDriver.findElement(By.linkText(LINK_TEXT)).click();
		//今回は、URLでページ遷移を判定
		assertTrue(webDriver.getCurrentUrl().contains(CHECK_URL));
		//ページ遷移後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		// TODO ここに追加
		//定数化
		final String START_HOUR_ROW_SELECTOR = "startHour0";
		final String UPDATE_BUTTON_SELECTOR = ".btn.btn-info.update-button";
		final String ERROR_SELECTOR = ".help-inline.error";
		final String CHECK_START_ERROR_MESSAGE = "* 出勤時間が正しく入力されていません。";
		final String CHECK_END_ERROR_MESSAGE = "* 退勤時間が正しく入力されていません。";

		final String SCROLL_PIXELS = "1000";
		final String END_MINUTE_ROW_SELECTOR = "endMinute0";
		final String EVIDENCE_FILE_NAME_START_HOUR_ERROR = "出勤時間エラー";
		final String EVIDENCE_FILE_NAME_END_HOUR_ERROR = "退勤時間エラー";

		//出勤時間の時間を未入力であるところに指定
		Select startHourSelectElement = new Select((webDriver.findElement(By.id(START_HOUR_ROW_SELECTOR))));
		startHourSelectElement.selectByIndex(0);
		//画面表示のためスクロール
		WebDriverUtils.scrollTo(SCROLL_PIXELS);
		//クリックする
		webDriver.findElement(By.cssSelector(UPDATE_BUTTON_SELECTOR)).click();
		//アラートのポップアップ画面が出るため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.alertIsPresent());
		//アラートのポップアップ画面処理
		webDriver.switchTo().alert().accept();
		//アラートメッセージの取得(新しく生成されるため待機不要)
		WebElement startHourErrorElement = webDriver.findElement(By.cssSelector(ERROR_SELECTOR));
		assertEquals(CHECK_START_ERROR_MESSAGE, startHourErrorElement.getText());
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_START_HOUR_ERROR);
		//出勤時間の時間を9:00に指定
		startHourSelectElement = new Select((webDriver.findElement(By.id(START_HOUR_ROW_SELECTOR))));
		startHourSelectElement.selectByIndex(9);
		//出勤時間の時間を未入力であるところに指定
		Select endMinuteSelectElement = new Select((webDriver.findElement(By.id(END_MINUTE_ROW_SELECTOR))));
		endMinuteSelectElement.selectByIndex(0);
		//画面表示のためスクロール(メッセージ出るとき上に戻るから)
		WebDriverUtils.scrollTo(SCROLL_PIXELS);
		//クリックする
		webDriver.findElement(By.cssSelector(UPDATE_BUTTON_SELECTOR)).click();
		//アラートのポップアップ画面が出るため待機
		wait.until(ExpectedConditions.alertIsPresent());
		//アラートのポップアップ画面処理
		webDriver.switchTo().alert().accept();
		//アラートメッセージの取得
		WebElement endMinuteErrorElement = webDriver.findElement(By.cssSelector(ERROR_SELECTOR));
		assertEquals(CHECK_END_ERROR_MESSAGE, endMinuteErrorElement.getText());
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_END_HOUR_ERROR);
		//出勤時間の時間を18:00に指定して、リセット
		endMinuteSelectElement = new Select((webDriver.findElement(By.id(END_MINUTE_ROW_SELECTOR))));
		endMinuteSelectElement.selectByIndex(18);

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {
		// TODO ここに追加
		//定数化

		final String START_HOUR_ROW_SELECTOR = "startHour0";
		final String START_MINUTE_ROW_SELECTOR = "startMinute0";
		final String UPDATE_BUTTON_SELECTOR = ".btn.btn-info.update-button";
		final String ERROR_SELECTOR = ".help-inline.error";
		final String SCROLL_PIXELS = "1000";
		final String CHECK_START_EMPTY_ERROR_MESSAGE = "* 出勤情報がないため退勤情報を入力出来ません。";
		final String EVIDENCE_FILE_NAME_START_EMPTY_ERROR = "出勤時間空白エラー";

		//出勤時間の時間を未入力であるところに指定
		Select startHourSelectElement = new Select((webDriver.findElement(By.id(START_HOUR_ROW_SELECTOR))));
		startHourSelectElement.selectByIndex(0);
		//出勤時間の分を未入力であるところに指定
		Select startMinuteSelectElement = new Select((webDriver.findElement(By.id(START_MINUTE_ROW_SELECTOR))));
		startMinuteSelectElement.selectByIndex(0);
		//画面表示のためスクロール
		WebDriverUtils.scrollTo(SCROLL_PIXELS);
		//クリックする
		webDriver.findElement(By.cssSelector(UPDATE_BUTTON_SELECTOR)).click();
		//アラートのポップアップ画面が出るため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.alertIsPresent());
		//アラートのポップアップ画面処理
		webDriver.switchTo().alert().accept();
		//アラートメッセージの取得
		WebElement startEmptyErrorElement = webDriver.findElement(By.cssSelector(ERROR_SELECTOR));
		assertEquals(CHECK_START_EMPTY_ERROR_MESSAGE, startEmptyErrorElement.getText());
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_START_EMPTY_ERROR);

		//出勤時間の時間を9:00に指定。
		startHourSelectElement = new Select((webDriver.findElement(By.id(START_HOUR_ROW_SELECTOR))));
		startMinuteSelectElement = new Select((webDriver.findElement(By.id(START_MINUTE_ROW_SELECTOR))));
		startHourSelectElement.selectByIndex(9);
		startMinuteSelectElement.selectByIndex(1);

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		// TODO ここに追加
		final String START_HOUR_ROW_SELECTOR = "startHour0";
		final String START_MINUTE_ROW_SELECTOR = "startMinute0";
		final String UPDATE_BUTTON_SELECTOR = ".btn.btn-info.update-button";
		final String ERROR_SELECTOR = ".help-inline.error";
		final String SCROLL_PIXELS = "1000";
		final String CHECK_START_LATER_THAN_END_ERROR_MESSAGE = "* 退勤時刻[0]は出勤時刻[0]より後でなければいけません。";
		final String EVIDENCE_FILE_NAME_START_LATER_THAN_END_ERROR = "出勤が退勤よりも遅いエラー";

		//出勤時間の時間を19:00に指定
		Select startHourSelectElement = new Select((webDriver.findElement(By.id(START_HOUR_ROW_SELECTOR))));
		startHourSelectElement.selectByIndex(20);

		Select startMinuteSelectElement = new Select((webDriver.findElement(By.id(START_MINUTE_ROW_SELECTOR))));
		startMinuteSelectElement.selectByIndex(1);
		//画面表示のためスクロール
		WebDriverUtils.scrollTo(SCROLL_PIXELS);
		//クリックする
		webDriver.findElement(By.cssSelector(UPDATE_BUTTON_SELECTOR)).click();
		//アラートのポップアップ画面が出るため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.alertIsPresent());
		//アラートのポップアップ画面処理
		webDriver.switchTo().alert().accept();

		//アラートメッセージの取得
		WebElement startLaterThanEndErrorElement = webDriver.findElement(By.cssSelector(ERROR_SELECTOR));
		assertEquals(CHECK_START_LATER_THAN_END_ERROR_MESSAGE, startLaterThanEndErrorElement.getText());
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_START_LATER_THAN_END_ERROR);
		//出勤時間の時間を9:00に指定。
		startHourSelectElement = new Select((webDriver.findElement(By.id(START_HOUR_ROW_SELECTOR))));
		startMinuteSelectElement = new Select((webDriver.findElement(By.id(START_MINUTE_ROW_SELECTOR))));
		startHourSelectElement.selectByIndex(9);
		startMinuteSelectElement.selectByIndex(1);
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		// TODO ここに追加
		final String START_HOUR_ROW_SELECTOR = "startHour0";
		final String START_MINUTE_ROW_SELECTOR = "startMinute0";
		final String SELECT_NAME_ROW_SELECTOR = "select[name=\"attendanceList[0].blankTime\"]";
		final String UPDATE_BUTTON_SELECTOR = ".btn.btn-info.update-button";
		final String ERROR_SELECTOR = ".help-inline.error";
		final String SCROLL_PIXELS = "1000";
		final String CHECK_BREAK_EXCEEDS_WORKING_HOURS_ERROR_MESSAGE = "* 中抜け時間が勤務時間を超えています。";
		final String EVIDENCE_FILE_NAME_BREAK_TIME_ERROR = "出退勤時間を超える中抜け時間のエラー";

		//出勤時間の時間を12:00に指定
		Select startHourSelectElement = new Select((webDriver.findElement(By.id(START_HOUR_ROW_SELECTOR))));
		startHourSelectElement.selectByIndex(12);
		Select startMinuteSelectElement = new Select((webDriver.findElement(By.id(START_MINUTE_ROW_SELECTOR))));
		startMinuteSelectElement.selectByIndex(1);
		//中抜け時間の設定(7時45分)
		Select blankTimeSelectElement = new Select((webDriver.findElement(By.cssSelector(SELECT_NAME_ROW_SELECTOR))));
		blankTimeSelectElement.selectByIndex(31);
		//画面表示のためスクロール
		WebDriverUtils.scrollTo(SCROLL_PIXELS);
		//クリックする
		webDriver.findElement(By.cssSelector(UPDATE_BUTTON_SELECTOR)).click();
		//アラートのポップアップ画面が出るため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.alertIsPresent());
		//アラートのポップアップ画面処理
		webDriver.switchTo().alert().accept();
		//アラートメッセージの取得
		WebElement startLaterThanEndErrorElement = webDriver.findElement(By.cssSelector(ERROR_SELECTOR));
		assertEquals(CHECK_BREAK_EXCEEDS_WORKING_HOURS_ERROR_MESSAGE, startLaterThanEndErrorElement.getText());
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BREAK_TIME_ERROR);
		//出勤時間の時間を9:00に指定。
		startHourSelectElement = new Select((webDriver.findElement(By.id(START_HOUR_ROW_SELECTOR))));
		startMinuteSelectElement = new Select((webDriver.findElement(By.id(START_MINUTE_ROW_SELECTOR))));
		startHourSelectElement.selectByIndex(9);
		startMinuteSelectElement.selectByIndex(1);

	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		// TODO ここに追加
	}

}
