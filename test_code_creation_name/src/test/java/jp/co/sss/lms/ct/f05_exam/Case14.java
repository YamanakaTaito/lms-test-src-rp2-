package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
 * 結合テスト 試験実施機能
 * ケース14
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果50点")
public class Case14 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
		//テスト実行と差があるため待機
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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加
		//定数化

		final String SUBMIT_BUTTON_SELECTOR = "input.btn.btn-default";
		final String CHECK_TITLE = "セクション詳細 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "セクション詳細";
		//テスト時、ITリテラシー①項目の2番目（アルゴリズム、フローチャート）は試験がある
		List<WebElement> buttonElements = webDriver.findElements(By.cssSelector(SUBMIT_BUTTON_SELECTOR));
		//対象の詳細ボタンをクリックする。
		buttonElements.get(1).click();
		//テスト実行と差があるため待機
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
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {
		// TODO ここに追加
		//定数化
		final String DEFAULT_BUTTON_SELECTOR = "Input[value=\"詳細\"]";
		final String CHECK_TITLE = "試験【ITリテラシー①】 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "試験【ITリテラシー①】";
		//btn btn-defaultが複数あるので、value値で指定。レポート画面へ遷移
		webDriver.findElement(By.cssSelector(DEFAULT_BUTTON_SELECTOR)).click();
		;

		//テスト実行と差があるため待機
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
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {
		// TODO ここに追加
		//定数化
		final String START_EXAM_BUTTON_SELECTOR = "Input[value=\"試験を開始する\"]";
		final String CHECK_TITLE = "ITリテラシー① | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "ITリテラシー①";
		//試験開始ボタンをクリック
		webDriver.findElement(By.cssSelector(START_EXAM_BUTTON_SELECTOR)).click();
		//テスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs(CHECK_TITLE));
		//ページ遷移したかタイトルで確認
		assertEquals(CHECK_TITLE, webDriver.getTitle());
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 正答と誤答が半々で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {
		// TODO ここに追加
		//定数化
		final String CONFIRM_EXAM_BUTTON_SELECTOR = "Input[value=\"確認画面へ進む\"]";
		final String SELECTOR_ANSWER_STATUS = "h2 small";
		final String SCROLL_PIXELS_5000 = "5000";
		final String SHORT_SCROLL_PIXELS_400 = "400";
		final String PAGE_SCROLL_PIXELS_200 = "200";
		final String CHECK_URL = "exam/answerCheck";
		final String CHECK_ANSWER_COUNT = "回答数：6／12問";
		final String EVIDENCE_FILE_NAME_BASE = "試験確認ページ";
		final String ANSWER_LIST_SELECTOR = "ul.list-group";
		final int STAY_1_SECOND = 1000;
		final int REQUIRED_CORRECT_ANSWERS = 6;
		String[] selectAnswerRadioCelector = { "input[value=\"3\"]", "input[value=\"3\"]", "input[value=\"1\"]",
				"input[value=\"1\"]", "input[value=\"2\"]", "input[value=\"2\"]" };
		//1問目から6問目まで解答入力
		//問題ごとの解答リスト取得する
		List<WebElement> answerElements = webDriver.findElements(By.cssSelector(ANSWER_LIST_SELECTOR));
		for (int i = 0; i < REQUIRED_CORRECT_ANSWERS; i++) {
			if (i % 2 == 0) {
				WebDriverUtils.scrollBy(PAGE_SCROLL_PIXELS_200);
			}
			if (i % 2 == 1) {
				WebDriverUtils.scrollBy(SHORT_SCROLL_PIXELS_400);
			}
			answerElements.get(i).findElement(By.cssSelector(selectAnswerRadioCelector[i])).click();
			//			WebDriverUtils.scrollBy(SHORT_SCROLL_PIXELS_200);

		}
		//ボタン表示のためスクロール
		WebDriverUtils.scrollBy(SCROLL_PIXELS_5000);
		//テストケースが早すぎて試験解答時間が1秒以下になるため、1秒ほど明示的に待機。
		try {
			Thread.sleep(STAY_1_SECOND);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		//ボタンを取得
		WebElement confirmExamButtonElement = webDriver.findElement(By.cssSelector(CONFIRM_EXAM_BUTTON_SELECTOR));

		//ボタンが表示されるまで待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CONFIRM_EXAM_BUTTON_SELECTOR)));

		//終了
		confirmExamButtonElement.click();
		//テスト実行と差があるため待機
		wait.until(ExpectedConditions.urlContains(CHECK_URL));
		//ページ遷移したかURLで確認
		assertTrue(webDriver.getCurrentUrl().contains(CHECK_URL));
		//回答数が0／12問であるかチェック
		WebElement answerElement = webDriver.findElement(By.cssSelector(SELECTOR_ANSWER_STATUS));
		assertTrue(answerElement.getText().contains(CHECK_ANSWER_COUNT));
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {
		// TODO ここに追加
		//定数化
		final String SEND_EXAM_BUTTON_SELECTOR = "sendButton";
		final String SCROLL_PIXELS = "5000";
		final String EXAM_TIME = "remainTime";
		final String CHECK_URL = "/lms/exam/result";
		final String EVIDENCE_FILE_NAME_BASE = "試験結果ページ";
		//JavaScriptで試験解答時間を引き継ぐ間の待機を入れる。remainTime
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(EXAM_TIME)));

		//ボタン表示のためスクロール
		WebDriverUtils.scrollBy(SCROLL_PIXELS);
		//ボタンを取得
		WebElement sendExamButtonElement = webDriver.findElement(By.id(SEND_EXAM_BUTTON_SELECTOR));

		//ボタンが表示されるまで待機
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SEND_EXAM_BUTTON_SELECTOR)));
		//ポップアップ画面処理
		//送信
		sendExamButtonElement.click();
		//ポップアップ画面処理
		webDriver.switchTo().alert().accept();
		//テスト実行と差があるため待機
		wait.until(ExpectedConditions.urlContains(CHECK_URL));
		//ページ遷移したかURLで確認
		assertTrue(webDriver.getCurrentUrl().contains(CHECK_URL));
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {
		// TODO ここに追加
		//定数化
		final String BACK_EXAM_BUTTON_SELECTOR = ".btn.btn-primary";
		final String EXAMS_RESULT_TABLE_SELECTOR = ".table tr";
		final String SCROLL_PIXELS = "5000";
		final String CUURENT_RESULT_SELECTOR = "td";
		final String CHECK_TITLE = "試験【ITリテラシー①】 | LMS";
		final String EVIDENCE_FILE_NAME_BASE = "4回目試験結果確認";
		final String NOW = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
		final String FOURTH_EXAM = "4回目";
		final String FOURTH_EXAM_SCORE = "50.0";
		final int FOURTH_EXAM_INDEX = 5;
		final int FOURTH_EXAM_NAME_INDEX = 0;
		final int FOURTH_EXAM_SCORE_INDEX = 1;
		final int FOURTH_EXAM_DATE_INDEX = 3;

		//ボタン表示のためスクロール
		WebDriverUtils.scrollBy(SCROLL_PIXELS);
		//ボタンを取得
		WebElement backExamButtonElement = webDriver.findElement(By.cssSelector(BACK_EXAM_BUTTON_SELECTOR));

		//ボタンが表示されるまで待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(BACK_EXAM_BUTTON_SELECTOR)));
		backExamButtonElement.click();
		//テスト実行と差があるため待機
		wait.until(ExpectedConditions.titleIs(CHECK_TITLE));
		//ページ遷移したかタイトルで確認
		assertEquals(CHECK_TITLE, webDriver.getTitle());

		//試験結果の確認（今回は４回目に反映される）
		List<WebElement> examResultElements = webDriver.findElements(By.cssSelector(EXAMS_RESULT_TABLE_SELECTOR));
		//4回目の中の回数と日付と点数が正しいか確認
		List<WebElement> currentResultElements = examResultElements.get(FOURTH_EXAM_INDEX)
				.findElements(By.tagName(CUURENT_RESULT_SELECTOR));
		//4回目かチェック
		assertThat(currentResultElements.get(FOURTH_EXAM_NAME_INDEX).getText().contains(FOURTH_EXAM));
		//点数チェック
		assertThat(currentResultElements.get(FOURTH_EXAM_SCORE_INDEX).getText().contains(FOURTH_EXAM_SCORE));
		//日付チェック
		assertThat(currentResultElements.get(FOURTH_EXAM_DATE_INDEX).getText().contains(NOW));
		//スクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_FILE_NAME_BASE);

	}
}
