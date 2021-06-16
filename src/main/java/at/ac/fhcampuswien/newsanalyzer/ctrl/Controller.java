package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.NewsApiException;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;
import at.ac.fhcampuswien.newsapi.enums.Language;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

	public static final String APIKEY = "470438b14c2a4c47880e892c13d7d3e9";  //TODO add your api key
	public static List urlList;
	NewsResponse newsResponse = new NewsResponse();
	String authorWithShortestName = "LoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsum";

	public long getAmountofArticles(List<Article> articles) {
		return articles.size();
	}

	public String getPublisherWithMostArticles(List<Article> articles) {

		Map<String, Long> countMap = articles.stream().collect(Collectors.groupingBy(provider -> provider.getSource().getName(), Collectors.counting()));

		return countMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey().toString();
	}

	public List<String> getURL(List<Article> articles){
		return articles.stream().map(Article::getUrl).collect(Collectors.toList());
	}

	public String getAuthorWithShortestName(List<Article> articles){
		articles.forEach(article -> {
			if(article != null && !(article.getAuthor() == null) && article.getAuthor().length() < authorWithShortestName.length())
				authorWithShortestName = article.getAuthor();
		});
		return authorWithShortestName;
	}

	public List<String> sortTitlesByLength(List<Article> articles){
		List<String> titles = new ArrayList<>();
		articles.stream().forEach(article -> {
			if(!titles.contains(article.getTitle()))
				titles.add(article.getTitle());
		});
		titles.sort((t1, t2) -> t2.length() - t1.length());
		return titles;
	}

	public void process(NewsApi enteredSeachDelimiters) {
		System.out.println("Start process");
		List<Article> articles = null;

		//TODO implement Error handling

		//TODO load the news based on the parameters

		//TODO implement methods for analysis

		try {
			newsResponse = enteredSeachDelimiters.getNews();
		} catch (NewsApiException e){
			System.out.println(e.getMessage());
		}

		articles = newsResponse.getArticles();

		if(articles.size() != 0){
			articles.forEach(article -> System.out.println(article.getTitle()));

			System.out.println("\nSorted titles by length: \n");
			List<String> titles = sortTitlesByLength(articles);
			titles.forEach(System.out::println);
			System.out.println("\nAmount of Articles: " + getAmountofArticles(articles) +
					"\nProvider with most articles: " + getPublisherWithMostArticles(articles) +
					"\nAuthor with shortest Name: " + getAuthorWithShortestName(articles) +
					"\nAll URL from the articles: " + getURL(articles));
			urlList = getURL(articles);
		} else {
			System.out.println("---NO ARTICLES FOUND---");
		}

		System.out.println("End process");
	}


	

	public Object getData() {
		
		return null;
	}
}
