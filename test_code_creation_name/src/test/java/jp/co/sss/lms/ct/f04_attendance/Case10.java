package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		// TODO ここに追加
		//定数化
		final String DEFAULT_BUTTON_VALUE_CLOCK_IN = "出勤";
		final String EVIDENCE_FILE_NAME_BASE = "出勤打刻画面";
		final String NOW = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

		//出勤をクリックする
		webDriver.findElement(By.cssSelector("input[value=" + DEFAULT_BUTTON_VALUE_CLOCK_IN + "]")).click();
		//アラートのポップアップ画面が出るため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.alertIsPresent());
		//アラートのポップアップ画面処理
		webDriver.switchTo().alert().accept();
		//打刻できたか確認
		List<WebElement> infoElements = webDriver.findElements(By.cssSelector(".info td"));
		assertEquals(NOW, infoElements.get(2).getText());
		//ページ遷移後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		// TODO ここに追加
		//定数化
		final String DEFAULT_BUTTON_VALUE_CLOCK_OUT = "退勤";
		final String EVIDENCE_FILE_NAME_BASE = "退勤打刻画面";
		final String NOW = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

		//退勤をクリックする
		webDriver.findElement(By.cssSelector("input[value=" + DEFAULT_BUTTON_VALUE_CLOCK_OUT + "]")).click();
		//アラートのポップアップ画面が出るため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.alertIsPresent());
		//アラートのポップアップ画面処理
		webDriver.switchTo().alert().accept();
		//打刻できたか確認
		List<WebElement> infoElements = webDriver.findElements(By.cssSelector(".info td"));
		assertEquals(NOW, infoElements.get(2).getText());
		//ページ遷移後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);

	}

}
