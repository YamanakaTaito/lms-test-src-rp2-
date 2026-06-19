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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
		WebDriverUtils.goTo("http://localhost:8080/lms");
		assertEquals("ログイン | LMS", webDriver.getTitle());
		WebElement classSelecterBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		assertEquals("ログイン", classSelecterBtnElement.getAttribute("value"));
		//ログイン画面のスクリーンショットをとる
		Path path = Path.of("evidence\\");
		if (!Files.exists(path)) {
			try {
				Files.createDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
		//loginIdタグを選択して、指定の値を入力
		WebElement loginIdElement = webDriver.findElement(By.id("loginId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("StudentAA01");
		//passwordタグを選択して、指定の値を入力
		WebElement passwordElement = webDriver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys("studentsTestsAA0012");

		//入力後.btn.btn-primaryをCSSセレクターで選択して、click
		WebElement classSelecterBtnElement = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		classSelecterBtnElement.click();
		//遷移後、ページ生成とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs("コース詳細 | LMS"));
		//ページ遷移したかタイトルで確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		//ログイン後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, "コース詳細画面");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加
		//リストメニューから「ヘルプ」を選択
		//リストメニューをクリック
		WebElement menuTextElement = webDriver.findElement(By.linkText("機能"));
		menuTextElement.click();

		WebElement linkTextElement = webDriver.findElement(By.linkText("ヘルプ"));
		linkTextElement.click();
		//ページ遷移したかタイトルで確認
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		//ページ遷移後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, "ヘルプ");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// TODO ここに追加
		//タブ切り替えがあるため、最初のページを基本の識別子とする
		String main_window_handle = webDriver.getWindowHandle();
		//よくある質問リンクをクリック
		WebElement linkTextElement = webDriver.findElement(By.linkText("よくある質問"));
		linkTextElement.click();
		//タブが2つになる。最初のページ以外にアクセスする
		for (String handle : webDriver.getWindowHandles()) {
			if (!(main_window_handle == handle)) {
				//新しいタブに移動する
				webDriver.switchTo().window(handle);
			}
		}
		//新しいタブに移動する
		webDriver.switchTo().window(webDriver.getWindowHandle());
		//遷移後、ページ生成とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));
		//ページ遷移したかタイトルで確認
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		//ページ遷移後のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, "よくある質問");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// TODO ここに追加
		//キーワード検索のid=formで選択し、「助成金」入力後クリックする
		WebElement keywordElement = webDriver.findElement(By.id("form"));
		keywordElement.clear();
		keywordElement.sendKeys("助成金");
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();
		//検索実行とテスト実行と差があるため待機
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb10 span")));

		//キーワード検索「助成金」を含んだ結果だけかチェック
		//「Q」と検索結果の本文を分けて抽出
		String result = "";
		List<WebElement> resultElements = webDriver.findElements(By.cssSelector(".mb10 span"));
		for (WebElement resultElement : resultElements) {
			if (!(resultElement.getText().equals("Q"))) {
				result = resultElement.getText();
			}
		}
		assertThat(result).contains("助成金");
		//結果のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, "助成金検索");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// TODO ここに追加
		//「btn btn-primary」の2個目を指定するので、全部取得。
		List<WebElement> buttonElements = webDriver.findElements(By.cssSelector(".btn.btn-primary"));
		//リストから「クリア」ボタンを選択して、消去
		buttonElements.get(1).click();
		//キーワード検索部分が空になったかチェック
		assertThat(buttonElements.get(0).getText()).isEmpty();
		//結果のスクリーンショットをとる。
		WebDriverUtils.getEvidence(new Object() {
		}, "クリアボタン");
	}

}
