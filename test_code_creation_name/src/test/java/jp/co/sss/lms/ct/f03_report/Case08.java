package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		final String SUBMIT_BUTTON_SELECTOR = "input.btn.btn-default";
		final String CHECK_TITLE = "セクション詳細 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "セクション詳細";
		//テスト時、ITリテラシー①項目の2番目（アルゴリズム、フローチャート）は提出済の週報がある
		List<WebElement> buttonElements = webDriver.findElements(By.cssSelector(SUBMIT_BUTTON_SELECTOR));
		//対象の詳細ボタンをクリックする。
		buttonElements.get(1).click();
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
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加
		final String DEFAULT_BUTTON_VALUE = "提出済み週報【デモ】を確認する";
		final String CHECK_TITLE = "レポート登録 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "レポート登録";
		//btn btn-defaultが複数あるので、value値で指定。レポート画面へ遷移
		webDriver.findElement(By.cssSelector("Input[value=" + DEFAULT_BUTTON_VALUE + "] ")).click();
		;

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
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// TODO ここに追加
		//定数化
		final String TEXTAREA_SELECTOR = "content_1";
		final String UPDATE_REPORT_TEXT = "週報のサンプルを変更しました。";
		final String EVIDENCE_FILE_NAME_TEXTAREA = "テキストエリア入力";
		final String SUBMIT_BUTTON_SELECTOR = ".btn.btn-primary";
		final String EVIDENCE_FILE_NAME_BASE = "セクション詳細";
		final String CHECK_TITLE = "セクション詳細 | LMS";
		//textarea「所感」のテキストボックスを指定して、修正する
		WebElement textElement = webDriver.findElement(By.id(TEXTAREA_SELECTOR));
		textElement.clear();
		textElement.sendKeys(UPDATE_REPORT_TEXT);
		//textareaに入力したスクリーンショット
		WebDriverUtils.scrollBy("300");
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_TEXTAREA);
		//「提出」ボタンをクリックする。
		webDriver.findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();
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
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
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
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// TODO ここに追加
		//定数化
		final String DEFAULT_BUTTON_VALUE = "詳細";
		final String UPDATE_REPORT_TEXT = "週報のサンプルを変更しました。";
		final String EVIDENCE_FILE_NAME_BASE = "レポート修正内容の反映";
		//週報の詳細をクリックする
		List<WebElement> submitElements = webDriver
				.findElements(By.cssSelector("Input[value=" + DEFAULT_BUTTON_VALUE + "] "));

		//
		WebDriverUtils.scrollBy("300");

		//対象のsubmitは２つ目。
		submitElements.get(1).click();
		//対象の「所感」はテーブルタグ３つ目のtdタグ２つ目の中にある
		List<WebElement> tableElements = webDriver.findElements(By.tagName("table"));
		List<WebElement> tdElements = tableElements.get(2).findElements(By.tagName("td"));
		//	「週報のサンプルを変更しました。」と比較
		assertEquals(UPDATE_REPORT_TEXT, tdElements.get(1).getText());
		//修正内容が確認できたことのスクリーンショット
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);

	}

}
