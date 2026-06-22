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
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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
		//入力値を定数で用意
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
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		//入力値を定数で用意
		final String SUBMIT_BUTTON_SELECTOR = "input.btn.btn-default";
		final String PANE_BODY_SELECTOR = "panel-body";
		final String CHECK_STATUS_BUTTON_TEXT = "未提出";
		final String CHECK_TITLE = "セクション詳細 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "セクション詳細";

		//テスト時、Java項目の1番目（Java概要）は未提出のステータス
		List<WebElement> panelBodyElements = webDriver.findElements(By.className(PANE_BODY_SELECTOR));
		//対象が未提出のステータスかチェックして。正しければクリック
		if (panelBodyElements.get(1).findElement(By.cssSelector(".w10per span")).getText()
				.equals(CHECK_STATUS_BUTTON_TEXT)) {
			panelBodyElements.get(1).findElement(By.cssSelector(SUBMIT_BUTTON_SELECTOR)).click();
		}
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
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加
		//入力値を定数で用意
		final String DEFAULT_BUTTON_SELECTOR = "input.btn-default";
		final String CHECK_TITLE = "レポート登録 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "レポート登録";

		//クリック
		webDriver.findElement(By.cssSelector(DEFAULT_BUTTON_SELECTOR)).click();
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
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// TODO ここに追加
		//入力値を定数で用意
		final String BUTTON_ELEMENT_CLASS_SELECTOR_NAME = ".btn.btn-primary";
		final String BUTTON_CHECK_ELEMENT_CLASS_SELECTOR_NAME = "input.btn.btn-default";

		final String TEXT_AREA = "textarea";
		final String EVIDENCE_PATH_INPUT = "レポート入力";
		final String EVIDENCE_PATH = "レポート登録完了";

		final String CHECK_TITLE = "セクション詳細 | LMS";
		final String INPUT_TEXT = "テスト";
		final String CHECK_STATUS_BUTTON = "提出済み日報【デモ】を確認する";

		//テキストエリアに値を入力
		WebElement textElement = webDriver.findElement(By.tagName(TEXT_AREA));
		textElement.clear();
		textElement.sendKeys(INPUT_TEXT);
		//入力後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_PATH_INPUT);
		//提出ボタンをクリックする。
		webDriver.findElement(By.cssSelector(BUTTON_ELEMENT_CLASS_SELECTOR_NAME)).click();

		//検索実行とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs(CHECK_TITLE));
		//ページ遷移したかタイトルで確認
		assertEquals(CHECK_TITLE, webDriver.getTitle());
		//提出後のボタンのステータスが変わっているかを確認
		assertEquals(CHECK_STATUS_BUTTON,
				webDriver.findElement(By.cssSelector(BUTTON_CHECK_ELEMENT_CLASS_SELECTOR_NAME)).getAttribute("value"));

		//登録完了のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_PATH);

	}

}
