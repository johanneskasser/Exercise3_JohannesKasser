package at.ac.fhcampuswien.newsanalyzer.ui;


import at.ac.fhcampuswien.newsanalyzer.ctrl.Controller;
import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.NewsApiException;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;
import at.ac.fhcampuswien.newsapi.enums.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class UserInterface 
{
	private Controller ctrl = new Controller();

	public void getDataFromCtrl1() {
		System.out.println("EURO 2021");

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("sport")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setLanguage(Language.de)
				.setPageSize("100")
				.createNewsApi();

		ctrl.process(newsApi);
	}

	public void getDataFromCtrl2() {
		// TODO implement me
		System.out.println("Corona News in Austria");

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("corona")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setLanguage(Language.de)
				.setSourceCountry(Country.at)
				.setPageSize("100")
				.createNewsApi();

		ctrl.process(newsApi);
	}

	public void getDataFromCtrl3() {
		// TODO implement me
		System.out.println("News in the US");

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("USA")
				.setLanguage(Language.en)
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.us)
				.setPageSize("100")
				.createNewsApi();

		ctrl.process(newsApi);
	}
	
	public void getDataForCustomInput() {
		// TODO implement me
		System.out.println("Custom Input");

		String keyword;

		Scanner scanner = new Scanner(System.in);
		System.out.print("\nPlease enter a keyword: \n>");
		keyword = scanner.nextLine();

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ(keyword)
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setPageSize("100")
				.createNewsApi();

		ctrl.process(newsApi);
	}


	public void start(){
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitle("Wählen Sie aus:");
		menu.insert("a", "EURO 2021", this::getDataFromCtrl1);
		menu.insert("b", "Corona News", this::getDataFromCtrl2);
		menu.insert("c", "News in the US", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Input:",this::getDataForCustomInput);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished");
	}


    protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
        } catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
        while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
            if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
                number = null;
			}
		}
		return number;
	}
}
