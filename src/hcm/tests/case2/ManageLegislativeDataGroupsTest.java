package hcm.tests.case2;

import static util.ReportLogger.log;
import static util.ReportLogger.logFailure;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.Test;

import common.BaseTest;
import common.BooleanCustomRunnable;
import common.CustomRunnable;
import common.ExcelUtilities;
import common.TaskUtilities;
import hcm.pageobjects.FuseWelcomePage;
import hcm.pageobjects.LoginPage;
import hcm.pageobjects.TaskListManagerTopPage;

public class ManageLegislativeDataGroupsTest extends BaseTest{
	private static final int MAX_TIME_OUT = 30;
	
	private static final int defaultcolNum = 7;
	private static final int defaultinputs = 11;
	
	private String projectName = "Default";
	private String sumMsg = "";
	private int projectRowNum = TestCaseRow;
	
	private String searchData, labelLocator, labelLocatorPath, dataLocator, rateTypeName;
	private String SSlabelLocator, SSlabelLocatorPath, currencyNamePath;
	private int label = 10;
	private int inputs = defaultinputs;
	private int colNum = defaultcolNum;
	private int projectSheetcolNum = 7;
	private int lastInput = 0;
	
	private boolean hasManagedLDG = false;
	private boolean isScrollingDown = true;
	
	@Test
	public void a_test() throws Exception  {
		testReportFormat();
	
	try{
		manageLegislativeDataGroups();
	  
	  	}
	
        catch (AssertionError ae)
        {
            takeScreenshot();
            logFailure(ae.getMessage());

            throw ae;
        }
        catch (Exception e)
        {
            takeScreenshot();
            logFailure(e.getMessage());

            throw e;
        }
    }
	
	public void manageLegislativeDataGroups() throws Exception{
		
		LoginPage login = new LoginPage(driver);
		takeScreenshot();
		login.enterUserID(5);
		login.enterPassword(6);
		login.clickSignInButton();
		
		FuseWelcomePage welcome = new FuseWelcomePage(driver);
		//takeScreenshot();
		welcome.clickNavigator("More...");
		clickNavigationLink("Setup and Maintenance");
			
		TaskListManagerTopPage task = new TaskListManagerTopPage(driver);
		//takeScreenshot();
		
		while(!hasManagedLDG && !projectName.isEmpty() && !projectName.contentEquals("")){
			projectName = selectProjectName();
			
			if(projectName.contains("*")){
				projectRowNum += 1;
				continue;
			}
			
			hasManagedLDG = manageLDG(task);
		}
		
		System.out.println(sumMsg);
		log("Legislative Data Groups has been managed.");
		System.out.println("Legislative Data Groups has been managed.");
	}
	
	private String selectProjectName() throws Exception{
		System.out.println("Setting Project to be edited...RowNum: "+projectRowNum+" vs. "+TestCaseRow);
		final String projectSheetName = "Create Implementation Project";

		XSSFSheet projectSheet = ExcelUtilities.ExcelWBook.getSheet(projectSheetName);
		XSSFCell projectCell;
		String newProjectName ="";
		
		if(projectRowNum <= 0){
			projectRowNum = TestCaseRow;
		}
		
	  	try{	        	   
	  		projectCell = projectSheet.getRow(projectRowNum).getCell(projectSheetcolNum);      	  
	  		projectCell.setCellType(projectCell.CELL_TYPE_STRING);
	  		newProjectName = projectCell.getStringCellValue();
	            
	            }catch (Exception e){
	            	e.printStackTrace();
	            	newProjectName="";
	            }
	  	
		System.out.println("New Project Name is now..."+newProjectName);
				
		return newProjectName;
	}

	private boolean manageLDG(TaskListManagerTopPage task) throws Exception{
		locateManageLDGPage(task);
		createLDGroups(task);
		
		return true;
	}
	
	private void locateManageLDGPage(TaskListManagerTopPage task) throws Exception{
		
		TaskUtilities.customWaitForElementVisibility("//a[text()='Manage Implementation Projects']", MAX_TIME_OUT);
		TaskUtilities.jsFindThenClick("//a[text()='Manage Implementation Projects']");
		TaskUtilities.customWaitForElementVisibility("//h1[text()='Manage Implementation Projects']", MAX_TIME_OUT);
		
		searchData = projectName;
		labelLocator = "Name";
		labelLocatorPath = TaskUtilities.retryingSearchInput(labelLocator);
		
		TaskUtilities.consolidatedInputEncoder(task, labelLocatorPath, searchData);
		TaskUtilities.jsFindThenClick("//button[text()='Search']");
		Thread.sleep(3500);
		TaskUtilities.customWaitForElementVisibility("//a[text()='"+searchData+"']", MAX_TIME_OUT);
		TaskUtilities.jsFindThenClick("//a[text()='"+searchData+"']");
	

		TaskUtilities.customWaitForElementVisibility("//h1[contains(text(),'"+searchData+"')]", MAX_TIME_OUT);
		TaskUtilities.customWaitForElementVisibility("//div[text()='Workforce Deployment']", MAX_TIME_OUT);
		
		if(is_element_visible("//div[text()='Workforce Deployment']"+"//a[@title='Expand']", "xpath")){
			TaskUtilities.retryingFindClick(By.xpath("//div[text()='Workforce Deployment']"+"//a[@title='Expand']"));
			TaskUtilities.customWaitForElementVisibility("//div[text()='Workforce Deployment']"+"//a[@title='Collapse']", MAX_TIME_OUT);
		}
		
		TaskUtilities.customWaitForElementVisibility("//div[text()='Define Common Applications Configuration for Human Capital Management']", MAX_TIME_OUT);
		
		if(is_element_visible("//div[text()='Define Common Applications Configuration for Human Capital Management']"+"//a[@title='Expand']", "xpath")){
			TaskUtilities.retryingFindClick(By.xpath("//div[text()='Define Common Applications Configuration for Human Capital Management']"+"//a[@title='Expand']"));
			TaskUtilities.customWaitForElementVisibility("//div[text()='Define Common Applications Configuration for Human Capital Management']"+"//a[@title='Collapse']", MAX_TIME_OUT);
		}
		
		TaskUtilities.customWaitForElementVisibility("//div[text()='Define Enterprise Structures for Human Capital Management']", MAX_TIME_OUT);
		
		if(is_element_visible("//div[text()='Define Enterprise Structures for Human Capital Management']"+"//a[@title='Expand']", "xpath")){
			TaskUtilities.retryingFindClick(By.xpath("//div[text()='Define Enterprise Structures for Human Capital Management']"+"//a[@title='Expand']"));
			TaskUtilities.customWaitForElementVisibility("//div[text()='Define Enterprise Structures for Human Capital Management']"+"//a[@title='Collapse']", MAX_TIME_OUT);
		}
		
		TaskUtilities.customWaitForElementVisibility("//div[text()='Manage Legislative Data Groups']", MAX_TIME_OUT);
		TaskUtilities.jsFindThenClick("//div[text()='Manage Legislative Data Groups']");
		TaskUtilities.jsFindThenClick("//div[text()='Manage Legislative Data Groups']/../..//a[@title='Go to Task']");
		
		TaskUtilities.customWaitForElementVisibility("//h1[text()='Manage Legislative Data Groups']", MAX_TIME_OUT);
	}
	private void createLDGroups(TaskListManagerTopPage task) throws Exception{
		TaskUtilities.jsFindThenClick("//span[text()=' Create']/..");
		//driver.findElement(By.xpath("//span[text()=' Create']/..")).click();
		TaskUtilities.customWaitForElementVisibility("//h1[text()='Create Legislative Data Group']", MAX_TIME_OUT, new CustomRunnable() {
			
			@Override
			public void customRun() throws Exception {
				// TODO Auto-generated method stub
				try{
						Thread.sleep(2000);
						TaskUtilities.jsFindThenClick("//span[text()=' Create']/..");
					} catch(WebDriverException we){
						
					}
			}
		});
		

		labelLocator = getExcelData(label, colNum, "text");
		labelLocator = TaskUtilities.filterDataLocator(labelLocator);
		labelLocatorPath = TaskUtilities.retryingSearchInput(labelLocator);

		String type = TaskUtilities.getdataLocatorType(labelLocator);
		dataLocator = getExcelData(inputs, colNum, type);
		
		TaskUtilities.retryingInputEncoder(task, labelLocatorPath, dataLocator);
		
		colNum += 1;
		searchCountry(task);
		colNum += 1;
		searchCurrency(task);
		colNum += 1;
		searchCostAllocStructure(task);
	}
	
	private void searchCountry(TaskListManagerTopPage task) throws Exception{
		
		labelLocator = getExcelData(label, colNum, "text");
		labelLocator = TaskUtilities.filterDataLocator(labelLocator);
		labelLocatorPath = TaskUtilities.retryingSearchInput(labelLocator);

		String type = TaskUtilities.getdataLocatorType(labelLocator);
		dataLocator = getExcelData(inputs, colNum, type);
		//Skips the search...
		if(dataLocator.isEmpty() || dataLocator.contentEquals(""))return;
		
		TaskUtilities.jsFindThenClick("//a[contains(@title,'Search and Select')][contains(@title,'"+labelLocator+"')]");
		
		TaskUtilities.retryingWrapper(new CustomRunnable() {
			
			@Override
			public void customRun() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(2250);
				TaskUtilities.jsFindThenClick("//a[text()='Search...']");
			}
		});
		
		SSlabelLocator = " Country";
		SSlabelLocatorPath = TaskUtilities.retryingSearchInput(SSlabelLocator);
		
		TaskUtilities.retryingWrapper(new CustomRunnable() {
			
			@Override
			public void customRun() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(2000);
				driver.findElement(By.xpath(SSlabelLocatorPath)).click();
			}
		});
		TaskUtilities.retryingInputEncoder(task, SSlabelLocatorPath, dataLocator);
		driver.findElement(By.xpath(SSlabelLocatorPath)).sendKeys(Keys.ENTER);
				
		TaskUtilities.customWaitForElementVisibility("//tbody//td[text()='"+dataLocator+"']", MAX_TIME_OUT);
		TaskUtilities.jsFindThenClick("//tbody//td[text()='"+dataLocator+"']");
		
		final String last5String = labelLocator.substring(labelLocator.length()-5);
		try{
					TaskUtilities.jsFindThenClick("//button[text()='OK'][not(contains(@id,'cancel'))][contains(@id,'"+last5String+"')]");
				} catch(WebDriverException we){
					TaskUtilities.jsFindThenClick("//button[text()='OK'][not(contains(@id,'cancel'))][contains(@id,'territory')]");
				}
		
		TaskUtilities.retryingWrapper(new CustomRunnable() {
			
			@Override
			public void customRun() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(2250);
				driver.findElement(By.xpath(labelLocatorPath)).click();
			}
		});
		
		Thread.sleep(2000);
		takeScreenshot();
	}
	
	private void searchCurrency(TaskListManagerTopPage task) throws Exception{
		labelLocator = getExcelData(label, colNum, "text");
		labelLocator = TaskUtilities.filterDataLocator(labelLocator);
		labelLocatorPath = TaskUtilities.retryingSearchInput(labelLocator);

		String type = TaskUtilities.getdataLocatorType(labelLocator);
		dataLocator = getExcelData(inputs, colNum, type);
		//Skips the search...
		if(dataLocator.isEmpty() || dataLocator.contentEquals("")) return;
		
		TaskUtilities.jsFindThenClick("//a[contains(@title,'Search and Select')][contains(@title,'"+labelLocator+"')]");
		
		TaskUtilities.retryingWrapper(new CustomRunnable() {
			
			@Override
			public void customRun() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(2250);
				TaskUtilities.jsFindThenClick("//a[text()='Search...']");
			}
		});
		
		SSlabelLocator = "Currency Code";
		SSlabelLocatorPath = TaskUtilities.retryingSearchInput(SSlabelLocator);
		
		TaskUtilities.retryingWrapper(new CustomRunnable() {
			
			@Override
			public void customRun() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(2000);
				driver.findElement(By.xpath(SSlabelLocatorPath)).click();
			}
		});
		
		TaskUtilities.retryingInputEncoder(task, SSlabelLocatorPath, dataLocator);
		driver.findElement(By.xpath(SSlabelLocatorPath)).sendKeys(Keys.ENTER);
		try{
				
				TaskUtilities.customWaitForElementVisibility("//tbody//td[text()='"+dataLocator+"']", MAX_TIME_OUT);
				currencyNamePath = "//td[text()='"+dataLocator+"']/../td[not(contains(text(),'"+dataLocator+"'))]";
			} catch(TimeoutException e){
				
				SSlabelLocator = "Currency Name";
				SSlabelLocatorPath = TaskUtilities.retryingSearchInput(SSlabelLocator);
				TaskUtilities.retryingInputEncoder(task, SSlabelLocatorPath, dataLocator);
				TaskUtilities.customWaitForElementVisibility("//tbody//td[text()='"+dataLocator+"']", MAX_TIME_OUT);
				currencyNamePath = "//td[text()='"+dataLocator+"']";
			}
		

		final String currencyName = driver.findElement(By.xpath(currencyNamePath)).getText();
		TaskUtilities.jsFindThenClick(currencyNamePath);
		
		final String last5String = labelLocator.substring(labelLocator.length()-5);
		TaskUtilities.jsFindThenClick("//button[text()='OK'][not(contains(@id,'cancel'))][contains(@id,'"+last5String+"')]");
		//Temporary Sol'n...
		
		TaskUtilities.retryingWrapper(new CustomRunnable() {
			
			@Override
			public void customRun() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(2250);
				driver.findElement(By.xpath(labelLocatorPath)).click();
			}
		});
		
		Thread.sleep(2000);
		takeScreenshot();
	}

	private void searchCostAllocStructure(TaskListManagerTopPage task) throws Exception{
		
		labelLocator = getExcelData(label, colNum, "text");
		labelLocator = TaskUtilities.filterDataLocator(labelLocator);
		labelLocatorPath = TaskUtilities.retryingSearchInput(labelLocator);

		String type = TaskUtilities.getdataLocatorType(labelLocator);
		dataLocator = getExcelData(inputs, colNum, type);
		//Skips the search...
		if(dataLocator.isEmpty() || dataLocator.contentEquals("")) return;
		
		TaskUtilities.jsFindThenClick("//a[contains(@title,'Search and Select')][contains(@title,'"+labelLocator+"')]");
		
		TaskUtilities.retryingWrapper(new CustomRunnable() {
			
			@Override
			public void customRun() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(2250);
				TaskUtilities.jsFindThenClick("//a[text()='Search...']");
			}
		});
		
		SSlabelLocator = "Currency Code";
		SSlabelLocatorPath = TaskUtilities.retryingSearchInput(SSlabelLocator);
		
		TaskUtilities.retryingWrapper(new CustomRunnable() {
			
			@Override
			public void customRun() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(2000);
				driver.findElement(By.xpath(SSlabelLocatorPath)).click();
			}
		});
		
		TaskUtilities.retryingInputEncoder(task, SSlabelLocatorPath, dataLocator);
		driver.findElement(By.xpath(SSlabelLocatorPath)).sendKeys(Keys.ENTER);
		try{
				
				TaskUtilities.customWaitForElementVisibility("//tbody//td[text()='"+dataLocator+"']", MAX_TIME_OUT);
				currencyNamePath = "//td[text()='"+dataLocator+"']/../td[not(contains(text(),'"+dataLocator+"'))]";
			} catch(TimeoutException e){
				
				SSlabelLocator = "Currency Name";
				SSlabelLocatorPath = TaskUtilities.retryingSearchInput(SSlabelLocator);
				TaskUtilities.retryingInputEncoder(task, SSlabelLocatorPath, dataLocator);
				TaskUtilities.customWaitForElementVisibility("//tbody//td[text()='"+dataLocator+"']", MAX_TIME_OUT);
				currencyNamePath = "//td[text()='"+dataLocator+"']";
			}
		

		final String currencyName = driver.findElement(By.xpath(currencyNamePath)).getText();
		TaskUtilities.jsFindThenClick(currencyNamePath);
		
		final String last5String = labelLocator.substring(labelLocator.length()-5);
		TaskUtilities.jsFindThenClick("//button[text()='OK'][not(contains(@id,'cancel'))][contains(@id,'"+last5String+"')]");
		//Temporary Sol'n...
		
		TaskUtilities.retryingWrapper(new CustomRunnable() {
			
			@Override
			public void customRun() throws Exception {
				// TODO Auto-generated method stub
				Thread.sleep(2250);
				driver.findElement(By.xpath(labelLocatorPath)).click();
			}
		});
		
		Thread.sleep(2000);
		takeScreenshot();
	}
}
