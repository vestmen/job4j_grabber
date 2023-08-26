package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    private final DateTimeParser dateTimeParser;
    private final int pages = 5;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    private String retrieveDescription(String link) {
        String result;
        try {
            result = Jsoup.connect(link).get().select("meta").get(2).attr("content");
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        return result;
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> result = new ArrayList<>();
        for (int i = 1; i <= pages; i++) {
            String pages = String.format("%s?page=%s", link, i);
            Connection connection = Jsoup.connect(pages);
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> result.add(postParse(row)));
        }
        return result;
    }

    private Post postParse(Element e) {
        Element titleElement = e.select(".vacancy-card__title").first();
        Element linkElement = titleElement.child(0);
        Element dateElement = e.select(".vacancy-card__date").first().child(0);
        String sourceLink = "https://career.habr.com";
        String pageLink = String.format("%s%s", sourceLink, linkElement.attr("href"));
        Post post = new Post();
        post.setTitle(titleElement.text());
        post.setLink(pageLink);
        post.setDescription(retrieveDescription(pageLink));
        post.setCreated(dateTimeParser.parse(dateElement.attr("datetime")));
        return post;
    }
}
