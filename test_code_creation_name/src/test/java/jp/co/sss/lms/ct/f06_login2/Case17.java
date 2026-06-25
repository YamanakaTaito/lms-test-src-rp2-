package jp.co.sss.lms.ct.f06_login2;

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
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト ログイン機能②
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		//定数化
		final String LOGIN_ID = "StudentAA03";
		final String PASSWORD = "StudentAA03";
		final String CHECK_TITLE = "セキュリティ規約 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "セキュリティ規約";

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
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		// TODO ここに追加
		//定数化
		final String SELECTOR_CHECK_BOX = "input[name=\"securityFlg\"]";
		final String NEXT_BUTTON_SELECTOR = ".btn.btn-primary";
		final String EVIDENCE_FILE_PASSWORD_CHECK = "チェックボックスにチェック後";
		final String CHECK_TITLE = "パスワード変更 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "パスワード変更";

		//チェックボックスにチェックを入れる
		webDriver.findElement(By.cssSelector(SELECTOR_CHECK_BOX)).click();
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_PASSWORD_CHECK);
		//次へボタンを押す
		webDriver.findElement(By.cssSelector(NEXT_BUTTON_SELECTOR)).click();
		//遷移後、ページ生成とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs(CHECK_TITLE));
		//ページ遷移したかタイトルで確認
		assertEquals(CHECK_TITLE, webDriver.getTitle());
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 変更パスワードを入力し「変更」ボタン押下")
	void test04() {
		// TODO ここに追加
		//定数化
		final String SELECTOR_CURRENT_PASSWORD_INPUT = "currentPassword";
		final String SELECTOR_NEW_PASSWORD_INPUT = "password";
		final String SELECTOR_CONFIRM_PASSWORD_INPUT = "passwordConfirm";

		final String TEST_DATA_CURRENT_PASSWORD = "StudentAA03";
		final String TEST_DATA_NEW_PASSWORD = "TestPassWord0801";
		final String TEST_DATA_CONFIRM_PASSWORD = "TestPassWord0801";
		final String SCROLL_PIXELS = "1000";
		final String UPDATE_BUTTON_SELECTOR = "button[type=\"submit\"]";
		final String ALELT_BUTTON_UPDATE_SELECTOR = "upd-btn";
		final String CHECK_TITLE = "コース詳細 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "コース詳細";

		//現在のパスワード入力
		WebElement currentPasswordElement = webDriver.findElement(By.id(SELECTOR_CURRENT_PASSWORD_INPUT));
		currentPasswordElement.clear();
		currentPasswordElement.sendKeys(TEST_DATA_CURRENT_PASSWORD);
		//新しいパスワード
		WebElement newPasswordElement = webDriver.findElement(By.id(SELECTOR_NEW_PASSWORD_INPUT));
		newPasswordElement.clear();
		newPasswordElement.sendKeys(TEST_DATA_NEW_PASSWORD);
		//確認パスワード
		WebElement confirmPasswordElement = webDriver.findElement(By.id(SELECTOR_CONFIRM_PASSWORD_INPUT));
		confirmPasswordElement.clear();
		confirmPasswordElement.sendKeys(TEST_DATA_CONFIRM_PASSWORD);

		//画面表示のためスクロール
		WebDriverUtils.scrollBy(SCROLL_PIXELS);
		//入力後変更ボタンクリック
		webDriver.findElement(By.cssSelector(UPDATE_BUTTON_SELECTOR)).click();
		//ダイアログの変更ボタンを取得
		WebElement alertButtonElement = webDriver.findElement(By.id(ALELT_BUTTON_UPDATE_SELECTOR));
		//ダイアログのボタンが押せるようになるまで待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(alertButtonElement));
		//ダイアログの変更ボタンをクリックする。
		alertButtonElement.click();
		//遷移後、ページ生成とテスト実行と差があるため待機
		wait.until(ExpectedConditions.titleIs(CHECK_TITLE));
		//ページ遷移したかタイトルで確認
		assertEquals(CHECK_TITLE, webDriver.getTitle());
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);

	}

}
