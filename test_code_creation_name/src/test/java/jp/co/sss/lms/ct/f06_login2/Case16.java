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
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
		final String LOGIN_ID = "StudentAA02";
		final String PASSWORD = "StudentAA02";
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
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {
		// TODO ここに追加
		//定数化
		final String SELECTOR_CURRENT_PASSWORD_INPUT = "currentPassword";
		final String SELECTOR_NEW_PASSWORD_INPUT = "password";
		final String SELECTOR_CONFIRM_PASSWORD_INPUT = "passwordConfirm";

		final String TEST_DATA_CURRENT_PASSWORD_EMPTY = "";
		final String TEST_DATA_NEW_PASSWORD = "TestPassWord0801";
		final String TEST_DATA_CONFIRM_PASSWORD = "TestPassWord0801";
		final String SCROLL_PIXELS = "1000";
		final String UPDATE_BUTTON_SELECTOR = "button[type=\"submit\"]";
		final String ALELT_BUTTON_UPDATE_SELECTOR = "upd-btn";
		final String ERROR_SELECTOR = "li span.help-inline.error";
		final String ERROR_MSG_CURRENT_PASSWORD_REQUIRED = "現在のパスワードは必須です。";
		final String EVIDENCE_FILE_PASSWORD_INPUT = "パスワード入力後画面";
		final String EVIDENCE_FILE_PASSWORD_REQUIRED_ERROR = "パスワードを未入力で「変更」のエラー";

		//現在のパスワード入力（未入力）
		WebElement currentPasswordElement = webDriver.findElement(By.id(SELECTOR_CURRENT_PASSWORD_INPUT));
		currentPasswordElement.clear();
		currentPasswordElement.sendKeys(TEST_DATA_CURRENT_PASSWORD_EMPTY);
		//新しいパスワード
		WebElement newPasswordElement = webDriver.findElement(By.id(SELECTOR_NEW_PASSWORD_INPUT));
		newPasswordElement.clear();
		newPasswordElement.sendKeys(TEST_DATA_NEW_PASSWORD);
		//確認パスワード
		WebElement confirmPasswordElement = webDriver.findElement(By.id(SELECTOR_CONFIRM_PASSWORD_INPUT));
		confirmPasswordElement.clear();
		confirmPasswordElement.sendKeys(TEST_DATA_CONFIRM_PASSWORD);

		//入力後のスクリーンショット
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_PASSWORD_INPUT);
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
		//エラーメッセージが出力されるのを待機
		wait.until(
				ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(ERROR_SELECTOR),
						ERROR_MSG_CURRENT_PASSWORD_REQUIRED));
		//アラートメッセージの取得
		WebElement passwordErrorMessage = webDriver.findElement(By.cssSelector(ERROR_SELECTOR));
		assertEquals(ERROR_MSG_CURRENT_PASSWORD_REQUIRED, passwordErrorMessage.getText());
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_PASSWORD_REQUIRED_ERROR);
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {
		// TODO ここに追加
		//定数化

		final String SELECTOR_CURRENT_PASSWORD_INPUT = "currentPassword";
		final String SELECTOR_NEW_PASSWORD_INPUT = "password";
		final String SELECTOR_CONFIRM_PASSWORD_INPUT = "passwordConfirm";

		final String TEST_DATA_CURRENT_PASSWORD = "StudentAA02";
		final String TEST_DATA_NEW_PASSWORD_EXCEED_MAX = "TestPassWord0801TestPassWord0801";
		final String TEST_DATA_CONFIRM_PASSWORD_EXCEED_MAX = "TestPassWord0801TestPassWord0801";
		final String SCROLL_PIXELS = "1000";
		final String UPDATE_BUTTON_SELECTOR = "button[type=\"submit\"]";
		final String ALELT_BUTTON_UPDATE_SELECTOR = "upd-btn";
		final String ERROR_SELECTOR = "li span.help-inline.error";
		final String ERROR_MSG_CURRENT_PASSWORD_LENGTH_EXCEEDS_MAX = "パスワードの長さが最大値(20)を超えています。";
		final String EVIDENCE_FILE_PASSWORD_INPUT = "パスワード入力後画面";
		final String EVIDENCE_FILE_PASSWORD_LENGTH_ERROR = "20文字以上の変更パスワードを入力し「変更」のエラー";
		//現在のパスワード入力
		WebElement currentPasswordElement = webDriver.findElement(By.id(SELECTOR_CURRENT_PASSWORD_INPUT));
		currentPasswordElement.clear();
		currentPasswordElement.sendKeys(TEST_DATA_CURRENT_PASSWORD);
		//新しいパスワード最大値(20)を超え
		WebElement newPasswordElement = webDriver.findElement(By.id(SELECTOR_NEW_PASSWORD_INPUT));
		newPasswordElement.clear();
		newPasswordElement.sendKeys(TEST_DATA_NEW_PASSWORD_EXCEED_MAX);
		//確認パスワード最大値(20)を超え
		WebElement confirmPasswordElement = webDriver.findElement(By.id(SELECTOR_CONFIRM_PASSWORD_INPUT));
		confirmPasswordElement.clear();
		confirmPasswordElement.sendKeys(TEST_DATA_CONFIRM_PASSWORD_EXCEED_MAX);
		//入力後のスクリーンショット
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_PASSWORD_INPUT);
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
		//エラーメッセージが出力されるのを待機
		wait.until(
				ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(ERROR_SELECTOR),
						ERROR_MSG_CURRENT_PASSWORD_LENGTH_EXCEEDS_MAX));
		//アラートメッセージの取得
		WebElement passwordErrorMessage = webDriver.findElement(By.cssSelector(ERROR_SELECTOR));
		assertEquals(ERROR_MSG_CURRENT_PASSWORD_LENGTH_EXCEEDS_MAX, passwordErrorMessage.getText());
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_PASSWORD_LENGTH_ERROR);
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {
		// TODO ここに追加 
		//定数化
		final String SELECTOR_CURRENT_PASSWORD_INPUT = "currentPassword";
		final String SELECTOR_NEW_PASSWORD_INPUT = "password";
		final String SELECTOR_CONFIRM_PASSWORD_INPUT = "passwordConfirm";
		final String TEST_DATA_CURRENT_PASSWORD = "StudentAA02";
		final String SCROLL_PIXELS = "1000";
		final String UPDATE_BUTTON_SELECTOR = "button[type=\"submit\"]";
		final String ALELT_BUTTON_UPDATE_SELECTOR = "upd-btn";
		final String ERROR_SELECTOR = "li span.help-inline.error";
		final String ERROR_MSG_CURRENT_PASSWORD_LENGTH_EXCEEDS_MAX = "「パスワード」には半角英数字のみ使用可能です。"
				+ "また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。";

		final String EVIDENCE_FILE_PASSWORD_LENGTH_ERROR = "20文字以上の変更パスワードを入力し「変更」のエラー";

		//規約違反のパスワードを配列
		String[] invalidPasswordTests = { "あああああああああ", "TESTPASSWORD", "testpassword", "12345678910", "TestPassWord",
				"testpassword1410123", "TESTPASSWORD1410123" };
		for (String invalidPasswordTest : invalidPasswordTests) {
			//現在のパスワード入力
			WebElement currentPasswordElement = webDriver.findElement(By.id(SELECTOR_CURRENT_PASSWORD_INPUT));
			currentPasswordElement.clear();
			currentPasswordElement.sendKeys(TEST_DATA_CURRENT_PASSWORD);
			//新しいパスワード
			WebElement newPasswordElement = webDriver.findElement(By.id(SELECTOR_NEW_PASSWORD_INPUT));
			newPasswordElement.clear();
			newPasswordElement.sendKeys(invalidPasswordTest);
			//確認パスワード
			WebElement confirmPasswordElement = webDriver.findElement(By.id(SELECTOR_CONFIRM_PASSWORD_INPUT));
			confirmPasswordElement.clear();
			confirmPasswordElement.sendKeys(invalidPasswordTest);
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
			//エラーメッセージが出力されるのを待機
			wait.until(
					ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(ERROR_SELECTOR),
							ERROR_MSG_CURRENT_PASSWORD_LENGTH_EXCEEDS_MAX));
			//アラートメッセージの取得
			WebElement passwordErrorMessage = webDriver.findElement(By.cssSelector(ERROR_SELECTOR));
			assertEquals(ERROR_MSG_CURRENT_PASSWORD_LENGTH_EXCEEDS_MAX, passwordErrorMessage.getText());
			//スクリーンショットをとる。
			WebDriverUtils.getEvidence(new Object() {
			}, EVIDENCE_FILE_PASSWORD_LENGTH_ERROR + "_" + invalidPasswordTest + "_");
		}
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {
		// TODO ここに追加
		//定数化
		final String SELECTOR_CURRENT_PASSWORD_INPUT = "currentPassword";
		final String SELECTOR_NEW_PASSWORD_INPUT = "password";
		final String SELECTOR_CONFIRM_PASSWORD_INPUT = "passwordConfirm";
		final String TEST_DATA_CURRENT_PASSWORD = "StudentAA02";
		final String TEST_DATA_NEW_PASSWORD = "TestPassWord0801";
		final String TEST_DATA_CONFIRM_PASSWORD_MISMATCH = "TestPassWord0802";

		final String EVIDENCE_FILE_PASSWORD_INPUT = "パスワード入力後画面";
		final String SCROLL_PIXELS = "1000";
		final String UPDATE_BUTTON_SELECTOR = "button[type=\"submit\"]";
		final String ALELT_BUTTON_UPDATE_SELECTOR = "upd-btn";
		final String ERROR_SELECTOR = "li span.help-inline.error";
		final String ERROR_MSG_CURRENT_PASSWORD_MISMATCH = "パスワードと確認パスワードが一致しません。";
		final String EVIDENCE_FILE_PASSWORD_MISMATCH_ERROR = "一致しない確認パスワードを入力のエラー";

		//現在のパスワード入力
		WebElement currentPasswordElement = webDriver.findElement(By.id(SELECTOR_CURRENT_PASSWORD_INPUT));
		currentPasswordElement.clear();
		currentPasswordElement.sendKeys(TEST_DATA_CURRENT_PASSWORD);
		//新しいパスワード
		WebElement newPasswordElement = webDriver.findElement(By.id(SELECTOR_NEW_PASSWORD_INPUT));
		newPasswordElement.clear();
		newPasswordElement.sendKeys(TEST_DATA_NEW_PASSWORD);
		//確認パスワード間違い
		WebElement confirmPasswordElement = webDriver.findElement(By.id(SELECTOR_CONFIRM_PASSWORD_INPUT));
		confirmPasswordElement.clear();
		confirmPasswordElement.sendKeys(TEST_DATA_CONFIRM_PASSWORD_MISMATCH);
		//入力後のスクリーンショット
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_PASSWORD_INPUT);
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
		//エラーメッセージが出力されるのを待機
		wait.until(
				ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(ERROR_SELECTOR),
						ERROR_MSG_CURRENT_PASSWORD_MISMATCH));
		//アラートメッセージの取得
		WebElement passwordErrorMessage = webDriver.findElement(By.cssSelector(ERROR_SELECTOR));
		assertEquals(ERROR_MSG_CURRENT_PASSWORD_MISMATCH, passwordErrorMessage.getText());
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_PASSWORD_MISMATCH_ERROR);
	}

}
