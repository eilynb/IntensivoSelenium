package Tests.ExamenFinal;

import Utilities.GetProperties;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class prueba_netflix {

    private WebDriver driver;
    private final String TITULO = "Películas y series ilimitadas y mucho más.";
    private static Faker FAKER= new Faker();
    private String mail = "";

    public prueba_netflix (String correo){
        this.mail=correo;
    }

    @BeforeMethod
    private void setUp() {
        GetProperties properties = new GetProperties();
        String chromeDriverUrl = properties.getString("CHROMEDRIVER_PATH");
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\eilyn\\IdeaProjects\\CursoAutomatizacion\\drivers\\chromedriver97.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.netflix.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @DataProvider(name = "mails")
    public Object[][] crearEmails() {
        return new Object[][]{
                {"jose@gmail.com"},
                {"selenium@test.com"},
                {"mail@mail.com"}
        };
    }

    @Test (priority = 5, groups= {"Success Tests"})
    public void validarTituloTest() {
        WebElement titulo = driver.findElement(By.tagName("h1"));
        Assert.assertEquals(titulo.getText(), TITULO);
    }

    @Test(priority = 4, groups= {"Success Tests"})
    public void iniciarSesionPageTest() {
        driver.findElement(By.xpath("//*[@href='/login']")).click();
        Assert.assertNotEquals(driver.getTitle(), TITULO);
        WebElement titulo = driver.findElement(By.tagName("h1"));
        Assert.assertTrue(titulo.getText().contains("Inicia sesión")==true);
        WebElement btnIniciaSesionFacebook = driver.findElement(By.className("fbBtnText"));
        Assert.assertTrue(btnIniciaSesionFacebook.getText().contains("Iniciar sesión con Facebook"));
    }

    @Test (priority = 3, groups= {"Success Tests"})
    public void loginToNetflixErrorTest(){
        driver.findElement(By.xpath("//*[@href='/login']")).click();
        driver.findElement(By.id("id_userLoginId")).sendKeys("prueba@test.com");
        driver.findElement(By.id("id_password")).sendKeys("holamundo");
        driver.findElement(By.className("login-remember-me-label-text")).click();
        driver.findElement(By.xpath("//button[@class='btn login-button btn-submit btn-small']")).click();
        WebElement messageContraseñaIncorrecta =driver.findElement(By.xpath("//*[@class='ui-message-contents']"));
        Assert.assertTrue(messageContraseñaIncorrecta.getText().contains("Contraseña incorrecta. ")==true);
        WebElement btnRecuerdame =driver.findElement(By.id("bxid_rememberMe_true"));
        Assert.assertTrue(btnRecuerdame.isSelected()==true);
    }

    @Test(priority = 2, groups= {"Failure Test"})
    public void fakeEmailTest(){
        driver.findElement(By.id("id_email_hero_fuji")).sendKeys(FAKER.internet().emailAddress());
        driver.findElement(By.className("cta-btn-txt")).click();
    }

    @Test (dataProvider = "mails", priority = 1, groups= {"Success Tests"})
    public void dataProviderEmailTest(String unMail) {
        driver.findElement(By.id("id_email_hero_fuji")).sendKeys(unMail);
        driver.findElement(By.className("cta-btn-txt")).click();
    }




    @AfterMethod
    public void tearDown() {
        //driver.close();
    }
}

