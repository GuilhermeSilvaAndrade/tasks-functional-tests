package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;



public class TasksTest {
	
	public WebDriver acessarAplicacao() throws MalformedURLException {
		//WebDriver driver = new ChromeDriver();
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.2:4444/wd/hub"), cap);
		driver.navigate().to("http://192.168.1.2:8001/tasks");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return driver;
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.xpath("//*[@id='addTodo']")).click();
			driver.findElement(By.xpath("//input[@id='task']")).sendKeys("teste");
			driver.findElement(By.xpath("//input[@id='dueDate']")).sendKeys("10/10/2021");
			driver.findElement(By.xpath("//input[@id='saveButton']")).click();
			String message = driver.findElement(By.xpath("//*[@id='message']")).getText();
			Assert.assertEquals("Success!", message);
		} finally {
			driver.quit();
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.xpath("//*[@id='addTodo']")).click();
			driver.findElement(By.xpath("//input[@id='dueDate']")).sendKeys("10/10/2021");
			driver.findElement(By.xpath("//input[@id='saveButton']")).click();
			String message = driver.findElement(By.xpath("//*[@id='message']")).getText();
			Assert.assertEquals("Fill the task description", message);
		} finally {
			driver.quit();
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.xpath("//*[@id='addTodo']")).click();
			driver.findElement(By.xpath("//input[@id='task']")).sendKeys("teste");
			driver.findElement(By.xpath("//input[@id='saveButton']")).click();
			String message = driver.findElement(By.xpath("//*[@id='message']")).getText();
			Assert.assertEquals("Fill the due date", message);
		} finally {
			driver.quit();
		}
		
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.findElement(By.xpath("//*[@id='addTodo']")).click();
			driver.findElement(By.xpath("//input[@id='task']")).sendKeys("teste");
			driver.findElement(By.xpath("//input[@id='dueDate']")).sendKeys("10/10/2019");
			driver.findElement(By.xpath("//input[@id='saveButton']")).click();
			String message = driver.findElement(By.xpath("//*[@id='message']")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		} finally {
			driver.quit();
		}
		
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			//Inserir task
			driver.findElement(By.xpath("//*[@id='addTodo']")).click();
			driver.findElement(By.xpath("//input[@id='task']")).sendKeys("teste via selenium");
			driver.findElement(By.xpath("//input[@id='dueDate']")).sendKeys("10/10/2030");
			driver.findElement(By.xpath("//input[@id='saveButton']")).click();
			String message = driver.findElement(By.xpath("//*[@id='message']")).getText();
			Assert.assertEquals("Success!", message);
			
			//Remover task
			driver.findElement(By.xpath("//a[@class='btn btn-outline-danger btn-sm']")).click();
			message = driver.findElement(By.xpath("//*[@id='message']")).getText();
			Assert.assertEquals("Success!", message);
		} finally {
			driver.quit();
		}
		
	}
}
