package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.assertj.core.api.Assertions.*;
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
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
		final String PATH = "evidence\\";
		final String CHACK_TITLE = "ログイン | LMS";
		final String CHACK_BUTTON = "ログイン";
		final String EVIDENCE_PATH = "ログイン画面";

		WebDriverUtils.goTo("http://localhost:8080/lms");
		assertEquals(CHACK_TITLE, webDriver.getTitle());
		WebElement classSelecterBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		assertEquals(CHACK_BUTTON, classSelecterBtnElement.getAttribute("value"));
		//ログイン画面のスクリーンショットをとる
		Path path = Path.of(PATH);
		if (!Files.exists(path)) {
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_PATH);
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		//入力値を定数で用意
		final String LOGIN_ID = "StudentAA01";
		final String PASSWORD = "StudentAA01";
		final String CHACK_TITLE = "コース詳細 | LMS";
		final String EVIDENCE_PATH = "コース詳細画面";

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
		wait.until(ExpectedConditions.titleIs(CHACK_TITLE));
		//ページ遷移したかタイトルで確認
		assertEquals(CHACK_TITLE, webDriver.getTitle());
		//ログイン後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_PATH);
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加
		final String CHACK_TITLE = "ヘルプ | LMS";
		final String EVIDENCE_PATH = "ヘルプ";

		//リストメニューから「ヘルプ」を選択
		//リストメニューをクリック
		WebElement menuTextElement = webDriver.findElement(By.linkText("機能"));
		menuTextElement.click();

		WebElement linkTextElement = webDriver.findElement(By.linkText("ヘルプ"));
		linkTextElement.click();
		//ページ遷移したかタイトルで確認
		assertEquals(CHACK_TITLE, webDriver.getTitle());
		//ページ遷移後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_PATH);
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// TODO ここに追加
		final String CHACK_TITLE = "よくある質問 | LMS";
		final String EVIDENCE_PATH = "よくある質問";

		//タブ切り替えがあるため、最初のページを基本の識別子とする
		final String MAIN_WINDO_HANDLE = webDriver.getWindowHandle();
		//よくある質問リンクをクリック
		WebElement linkTextElement = webDriver.findElement(By.linkText("よくある質問"));
		linkTextElement.click();
		//タブが2つになる。最初のページ以外にアクセスする
		for (String handle : webDriver.getWindowHandles()) {
			if (!(MAIN_WINDO_HANDLE == handle)) {
				//新しいタブに移動する
				webDriver.switchTo().window(handle);
			}
		}
		//新しいタブに移動する
		webDriver.switchTo().window(webDriver.getWindowHandle());
		//遷移後、ページ生成とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs(CHACK_TITLE));
		//ページ遷移したかタイトルで確認
		assertEquals(CHACK_TITLE, webDriver.getTitle());
		//ページ遷移後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_PATH);
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// TODO ここに追加
		//定数化
		final String CATEGORY_TEXT = "研修関係";
		final String CHECK_URL = "frequentlyAskedQuestionCategoryId=1";
		final String EVIDENCE_PATH = "カテゴリ検索";

		//研修関係のカテゴリ検索をクリックする
		webDriver.findElement(By.partialLinkText(CATEGORY_TEXT)).click();
		//この時のURLを取得し、部分一致で評価する
		assertThat(webDriver.getCurrentUrl()).contains(CHECK_URL);

		//検索結果表示後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_PATH);

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// TODO ここに追加
		final String EVIDENCE_PATH = "検索結果の回答";

		//検索結果の一つ目をクリック
		webDriver.findElement(By.className("mb10")).click();
		//javascriptでclass名変えて、表示しているのでfs18で指定する。
		List<WebElement> answerElements = webDriver.findElements(By.cssSelector(".fs18 span"));

		//検索実行とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(answerElements.get(1)));

		//結果部分の「A」が表示されているか
		assertTrue(answerElements.get(0).isDisplayed());
		//結果部分の本文が表示されているか
		assertTrue(answerElements.get(1).isDisplayed());
		//回答表示後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, EVIDENCE_PATH);
	}

}
