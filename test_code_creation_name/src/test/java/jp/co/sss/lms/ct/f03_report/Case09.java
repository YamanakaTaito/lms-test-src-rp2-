package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		//定数化
		final String LINK_TEXT = "ようこそ受講生ＡＡ１さん";
		final String EVIDENCE_FILE_NAME_BASE = "ユーザー詳細";
		final String CHECK_TITLE = "ユーザー詳細";
		//ようこそ○○さんリンクをクリックする
		webDriver.findElement(By.linkText(LINK_TEXT)).click();
		//検索実行とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs(CHECK_TITLE));
		//ページ遷移したかタイトルで確認
		assertEquals(CHECK_TITLE, webDriver.getTitle());
		//ページ遷移後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加
		//定数化
		final String DEFAULT_BUTTON_VALUE = "修正する";
		final String CHECK_TITLE = "レポート登録 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "レポート登録";

		//画面表示のためスクロール
		WebDriverUtils.scrollBy("300");
		//週報の修正するをクリックする
		//対象のsubmit(修正する)は1つ目。
		webDriver.findElement(By.cssSelector("Input[value=" + DEFAULT_BUTTON_VALUE + "] ")).click();
		//検索実行とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs(CHECK_TITLE));
		//ページ遷移したかタイトルで確認
		assertEquals(CHECK_TITLE, webDriver.getTitle());
		//ページ遷移後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// TODO ここに追加
		//定数化
		final String TEXTAREA_SELECTOR = "intFieldName_0";
		final String SUBMIT_BUTTON_SELECTOR = ".btn.btn-primary";
		final String ERROR_SELECTOR = ".form-control.errorInput";
		final String EVIDENCE_FILE_NAME_BASE = "学習項目＿未入力＿エラー";

		//学習項目のクリア
		webDriver.findElement(By.id(TEXTAREA_SELECTOR)).clear();
		//画面表示のためスクロール
		WebDriverUtils.scrollBy("300");
		//このまま「提出する」ボタンクリックするとエラー表示
		webDriver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();
		//実行とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ERROR_SELECTOR)));
		//エラー表示（赤くハイライトするためのクラス）用の要素を確認してcheckする
		assertTrue(webDriver.findElement(By.cssSelector(ERROR_SELECTOR)).isDisplayed());
		//エラー表示のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// TODO ここに追加
		//定数化
		final String TEXTAREA_SELECTOR = "intFieldName_0";
		final String DEFAULT_STUDY_ITEM = "ITリテラシー①";
		final String SELECT_BOX_SELECTOR = "intFieldValue_0";
		final String SUBMIT_BUTTON_SELECTOR = ".btn.btn-primary";
		final String ERROR_SELECTOR = ".form-control.errorInput";
		final String EVIDENCE_FILE_NAME_BASE = "理解度＿未入力＿エラー";

		//学習項目の再入力
		webDriver.findElement(By.id(TEXTAREA_SELECTOR)).sendKeys(DEFAULT_STUDY_ITEM);
		//理解度のセレクトプルダウンメニューから、空白を選択
		new Select(webDriver.findElement(By.id(SELECT_BOX_SELECTOR))).selectByIndex(0);
		//画面表示のためスクロール
		WebDriverUtils.scrollBy("300");
		//このまま「提出する」ボタンクリックするとエラー表示
		webDriver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();
		//実行とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ERROR_SELECTOR)));
		//エラー表示（赤くハイライトするためのクラス）用の要素を確認してcheckする
		assertTrue(webDriver.findElement(By.cssSelector(ERROR_SELECTOR)).isDisplayed());
		//エラー表示のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// TODO ここに追加
		//定数化
		final String SELECT_BOX_SELECTOR = "intFieldValue_0";
		final String TEXTAREA_SELECTOR = "content_0";
		final String ERROR_TEST_INPUT_TEXT = "あああ";
		final String SUBMIT_BUTTON_SELECTOR = ".btn.btn-primary";
		final String ERROR_SELECTOR = ".form-control.errorInput";
		final String EVIDENCE_FILE_NAME_BASE = "目標の達成度＿数値以外＿エラー";

		//理解度のセレクトプルダウンメニューから、3を選択してリセット
		new Select(webDriver.findElement(By.id(SELECT_BOX_SELECTOR))).selectByIndex(3);
		//目標の達成度のテキストボックスを選択
		WebElement textareaElement = webDriver.findElement(By.id(TEXTAREA_SELECTOR));
		//対象のテキストエリアをクリア後、入力
		textareaElement.clear();
		textareaElement.sendKeys(ERROR_TEST_INPUT_TEXT);
		//画面表示のためスクロール
		WebDriverUtils.scrollBy("300");
		//このまま「提出する」ボタンクリックするとエラー表示
		webDriver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();
		//実行とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ERROR_SELECTOR)));
		//エラー表示（赤くハイライトするためのクラス）用の要素を確認してcheckする
		assertTrue(webDriver.findElement(By.cssSelector(ERROR_SELECTOR)).isDisplayed());
		//エラー表示のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// TODO ここに追加
		//定数化
		final String TEXTAREA_SELECTOR = "content_0";
		final String ERROR_TEST_INPUT_UNDER_MIN = "0";
		final String SUBMIT_BUTTON_SELECTOR = ".btn.btn-primary";
		final String ERROR_SELECTOR = ".form-control.errorInput";
		final String EVIDENCE_FILE_NAME_UNDER_MIN = "目標の達成度＿最小以下＿エラー";
		final String ERROR_TEST_INPUT_OVER_MAX = "11";
		final String EVIDENCE_FILE_NAME_OVER_MAX = "目標の達成度＿最大以上＿エラー";
		//目標の達成度のテキストボックスを選択
		WebElement textareaElement = webDriver.findElement(By.id(TEXTAREA_SELECTOR));
		//対象のテキストエリアをクリア後、入力
		textareaElement.clear();
		textareaElement.sendKeys(ERROR_TEST_INPUT_UNDER_MIN);
		//画面表示のためスクロール
		WebDriverUtils.scrollBy("300");
		//このまま「提出する」ボタンクリックするとエラー表示
		webDriver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();
		//実行とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ERROR_SELECTOR)));
		//エラー表示（赤くハイライトするためのクラス）用の要素を確認してcheckする
		assertTrue(webDriver.findElement(By.cssSelector(ERROR_SELECTOR)).isDisplayed());
		//エラー表示のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_UNDER_MIN);

		//テキストエリア指定が古くなったので、再指定
		textareaElement = webDriver.findElement(By.id(TEXTAREA_SELECTOR));
		textareaElement.clear();
		textareaElement.sendKeys(ERROR_TEST_INPUT_OVER_MAX);
		//画面表示のためスクロール
		WebDriverUtils.scrollBy("300");
		//このまま「提出する」ボタンクリックするとエラー表示
		webDriver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();
		//実行とテスト実行と差があるため待機
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ERROR_SELECTOR)));
		//エラー表示（赤くハイライトするためのクラス）用の要素を確認してcheckする
		assertTrue(webDriver.findElement(By.cssSelector(ERROR_SELECTOR)).isDisplayed());
		//エラー表示のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_OVER_MAX);

	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// TODO ここに追加
		//定数化
		final String COMPREHENSION_TEXTAREA_SELECTOR = "content_0";
		final String SUBMIT_BUTTON_SELECTOR = ".btn.btn-primary";
		final String ERROR_SELECTOR = ".form-control.errorInput";
		final String EVIDENCE_FILE_NAME_EMPTY_TARGET_COMPREHENSION = "目標の達成度＿未入力＿エラー";
		final String DEFAULT_COMPREHENSION_TEXTAREA = "5";

		final String IMPRESSION_TEXTAREA_SELECTOR = "content_1";
		final String EVIDENCE_FILE_NAME_EMPTY_TARGET_IMPRESSION = "所感＿未入力＿エラー";
		//目標の達成度のテキストボックスを選択
		WebElement comprehensionTextarea = webDriver.findElement(By.id(COMPREHENSION_TEXTAREA_SELECTOR));
		//対象のテキストエリアをクリア
		comprehensionTextarea.clear();
		//画面表示のためスクロール
		WebDriverUtils.scrollBy("300");
		//このまま「提出する」ボタンクリックするとエラー表示
		webDriver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();
		//実行とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ERROR_SELECTOR)));
		//エラー表示（赤くハイライトするためのクラス）用の要素を確認してcheckする
		assertTrue(webDriver.findElement(By.cssSelector(ERROR_SELECTOR)).isDisplayed());
		//エラー表示のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_EMPTY_TARGET_COMPREHENSION);
		//テキストエリア指定が古くなったので、再指定
		comprehensionTextarea = webDriver.findElement(By.id(COMPREHENSION_TEXTAREA_SELECTOR));
		//目標の達成度のテキストエリアを最初の値にリセット
		comprehensionTextarea.sendKeys(DEFAULT_COMPREHENSION_TEXTAREA);

		//所感の達成度のテキストボックスを選択
		WebElement impressionTextarea = webDriver.findElement(By.id(IMPRESSION_TEXTAREA_SELECTOR));
		//対象のテキストエリアをクリア
		impressionTextarea.clear();
		//画面表示のためスクロール
		WebDriverUtils.scrollBy("300");
		//このまま「提出する」ボタンクリックするとエラー表示
		webDriver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();
		//実行とテスト実行と差があるため待機
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ERROR_SELECTOR)));
		//エラー表示（赤くハイライトするためのクラス）用の要素を確認してcheckする
		assertTrue(webDriver.findElement(By.cssSelector(ERROR_SELECTOR)).isDisplayed());
		//エラー表示のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_EMPTY_TARGET_IMPRESSION);
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// TODO ここに追加
		final String IMPRESSION_TEXTAREA_SELECTOR = "content_1";
		final String OVER_2000_CHARACTERS = "あ".repeat(2001);
		final String SUBMIT_BUTTON_SELECTOR = ".btn.btn-primary";
		final String ERROR_SELECTOR = ".form-control.errorInput";
		final String EVIDENCE_FILE_NAME_EMPTY_TARGET_IMPRESSION = "所感＿2000文字超＿エラー";
		final String DEFAULT_COMPREHENSION_IMPRESSION = "週報のサンプルを変更しました。";
		final String WEEKLY_REFLECTION_TEXTAREA_SELECTOR = "content_2";
		final String EVIDENCE_FILE_NAME_EMPTY_WEEKLY_REFLECTION = "一週間の振り返り＿2000文字超＿エラー";

		//所感の達成度のテキストボックスを選択
		WebElement impressionTextarea = webDriver.findElement(By.id(IMPRESSION_TEXTAREA_SELECTOR));
		//対象のテキストエリアをクリア
		impressionTextarea.clear();
		//2001文字の単語を入力
		impressionTextarea.sendKeys(OVER_2000_CHARACTERS);
		//画面表示のためスクロール
		WebDriverUtils.scrollBy("300");
		//このまま「提出する」ボタンクリックするとエラー表示
		webDriver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();

		//実行とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ERROR_SELECTOR)));
		//エラー表示（赤くハイライトするためのクラス）用の要素を確認してcheckする
		assertTrue(webDriver.findElement(By.cssSelector(ERROR_SELECTOR)).isDisplayed());
		//エラー表示のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_EMPTY_TARGET_IMPRESSION);

		//テキストエリア指定が古くなったので、再指定
		impressionTextarea = webDriver.findElement(By.id(IMPRESSION_TEXTAREA_SELECTOR));
		//対象のテキストエリアをクリア
		impressionTextarea.clear();
		//所感の達成度のテキストエリアを最初の値にリセット
		impressionTextarea.sendKeys(DEFAULT_COMPREHENSION_IMPRESSION);

		//所感の達成度のテキストボックスを選択
		WebElement weeklyReflectionTextarea = webDriver.findElement(By.id(WEEKLY_REFLECTION_TEXTAREA_SELECTOR));
		//対象のテキストエリアをクリア
		weeklyReflectionTextarea.clear();
		//2001文字の単語を入力
		weeklyReflectionTextarea.sendKeys(OVER_2000_CHARACTERS);
		//画面表示のためスクロール
		WebDriverUtils.scrollBy("300");
		//このまま「提出する」ボタンクリックするとエラー表示
		webDriver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();

		//実行とテスト実行と差があるため待機
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ERROR_SELECTOR)));
		//エラー表示（赤くハイライトするためのクラス）用の要素を確認してcheckする
		assertTrue(webDriver.findElement(By.cssSelector(ERROR_SELECTOR)).isDisplayed());
		//エラー表示のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_EMPTY_WEEKLY_REFLECTION);

	}

}
