package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

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
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
		//アラートのポップアップ画面が出るため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.alertIsPresent());
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
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		// TODO ここに追加

		//定数化
		final String ATTENDANCE_ROW_SELECTOR = "table tr";
		final String SELECTED_OPTION_SELECTOR_SUFFIX = " option[selected=\"selected\"]";
		final String VALUE_ATTRIBUTE = "value";
		final String LINK_TEXT_UPDATE = "勤怠情報を直接編集する";
		final String SCROLL_PIXELS = "300";
		final String UPDATE_BUTTON_SELECTOR = ".btn.btn-info.update-button";
		final String ROW_SELECTOR = "tbody.db tr";
		final String TAG_NAME_TD = "td";
		final String ABSENT_TEXT = "欠席";
		final String EVIDENCE_FILE_NAME_INPUT_BEFOR = "打刻する画面";
		final String EVIDENCE_FILE_NAME_INPUT_AFTER = "打刻した画面";
		final String EVIDENCE_FILE_NAME_BASE = "勤怠情報を直接編集する画面";

		//実施前のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_INPUT_BEFOR);

		List<WebElement> attendanceRowWebElements = webDriver.findElements(By.cssSelector(ATTENDANCE_ROW_SELECTOR));
		for (int i = 1; i < attendanceRowWebElements.size(); i++) {
			//出勤退勤時間が未入力かチェック
			//取得したtr要素から、出勤と退勤の要素を取得する用のセレクタ作成
			String startHourRowSelector = "#startHour" + (i - 1);
			String endHourRowSelector = "#endHour" + (i - 1);
			//出勤と退勤のセレクトされている要素が入力されている場合は、定時ボタンクリック不要。
			//時間と分の要素を１ずつ取り出す
			WebElement startHourRowElement = attendanceRowWebElements.get(i).findElement(
					By.cssSelector(startHourRowSelector + SELECTED_OPTION_SELECTOR_SUFFIX));
			WebElement endHourRowElement = attendanceRowWebElements.get(i).findElement(
					By.cssSelector(endHourRowSelector + SELECTED_OPTION_SELECTOR_SUFFIX));
			//以下は空かどうか判定し、空であれば次にすすむ。
			if (startHourRowElement.getAttribute(VALUE_ATTRIBUTE).isEmpty()
					&& endHourRowElement.getAttribute(VALUE_ATTRIBUTE).isEmpty()) {
				//これで空を指定。さらに、「欠席」入力されていなければ、定時ボタンをクリックする。。
				System.out.println(startHourRowElement.getAttribute(VALUE_ATTRIBUTE));

				if (!(attendanceRowWebElements.get(i).getText().contains("欠席"))) {
					attendanceRowWebElements.get(i).findElement(By.tagName("button")).click();

				}
			}
		}
		//入力後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_INPUT_AFTER);
		//画面表示のためスクロール
		WebDriverUtils.scrollBy(SCROLL_PIXELS);
		//最後に更新ボタンを押す
		webDriver.findElement(By.cssSelector(UPDATE_BUTTON_SELECTOR)).click();

		//アラートのポップアップ画面が出るため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.alertIsPresent());
		//アラートのポップアップ画面処理
		webDriver.switchTo().alert().accept();

		//今回は、リンクでページ遷移を判定
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(LINK_TEXT_UPDATE)));
		//表示部のtr取得
		List<WebElement> attendanceHistoryRows = webDriver.findElements(By.cssSelector(ROW_SELECTOR));
		for (WebElement row : attendanceHistoryRows) {
			List<WebElement> cells = row.findElements(By.tagName(TAG_NAME_TD));
			//１件打刻がないかつ欠席のデータもないものが存在した場合テストは失敗
			if ((cells.get(2).getText().isEmpty()) && !(cells.get(5).getText().contains(ABSENT_TEXT))) {
				assertTrue(false);
			}
		}
		//実施後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);

	}

}
