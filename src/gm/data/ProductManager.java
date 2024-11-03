package gm.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

public class ProductManager {

    private Map<Product, List<Review>> products = new HashMap<>();
    private ResourceFormatter formatter;
    private static Map<String, ResourceFormatter> formatters =
            Map.of("en-GB", new ResourceFormatter(Locale.UK),
                    "en-US", new ResourceFormatter(Locale.US),
                    "fr-FR", new ResourceFormatter(Locale.FRANCE),
                    "ru-RU", new ResourceFormatter(new Locale("ru","RU")),
                    "ja-JP", new ResourceFormatter(new Locale("ja","JP")),
                    "ar-AE", new ResourceFormatter(new Locale("ar","AE")),
                    "hi_IN", new ResourceFormatter(new Locale("hi","IN")),
                    "zh-CN", new ResourceFormatter(Locale.CHINA),
                         "de-DE", new ResourceFormatter(Locale.GERMANY));

    private static class ResourceFormatter{
        private Locale locale;
        private ResourceBundle resources;
        private DateTimeFormatter dateFormat;
        private NumberFormat moneyFormat;

        private ResourceFormatter(Locale locale){
            locale = locale;
            resources =  ResourceBundle.getBundle("gm.data.resources", locale);
            dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
            moneyFormat = NumberFormat.getCurrencyInstance(locale);
        }
        private String formatProduct(Product product){
            return MessageFormat.format(resources.getString("product"),
                    product.getName(),
                    moneyFormat.format(product.getPrice()),
                    product.getRating().getStars(),
                    dateFormat.format(product.getBestBefore()));
        }
        private String formatReview(Review review){
            return MessageFormat.format(resources.getString("review"),
                    review.getRating().getStars(),
                    review.getComment());
        }
        private String getText(String key){
            return resources.getString(key);
        }


    }

    public ProductManager(Locale locale) {
            this(locale.toLanguageTag());
    }
    public ProductManager(String languageTag){
        changeLocale(languageTag);
    }
    public void changeLocale(String languageTag){
        formatter = formatters.getOrDefault(languageTag, formatters.get("en-GB"));
    }
    public static Set<String> getSupportedLocales(){
        return formatters.keySet();
    }



    public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore)
    {   Product p = new Food(id, name, price, rating, bestBefore);
        products.putIfAbsent(p, new ArrayList<>());
        return p;
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating)
    {
        Product p = new Drink(id, name, price, rating);
        products.putIfAbsent(p, new ArrayList<>());
        return p;
    }
    public Product reviewProductWithStreams(Product p, Rating rat, String comment)
    {    List<Review> reviews = products.get(p);
        products.remove(p, reviews);
        Review review = new Review(rat, comment);
        p = p.applyRating(Rateable.convert((int)Math.round(reviews.stream()
                                                                  .mapToInt(r ->r.getRating().ordinal())
                                                                  .average()
                                                                   .orElse(0))));

        products.put(p,reviews);
        return p;
    }
    public Product reviewProduct(int id, Rating rat, String comment)
    {
        return reviewProduct(findProduct(id), rat, comment);
    }

    public Product reviewProduct(Product p, Rating rat, String comment)
    {

        List<Review> reviews = products.get(p);
        products.remove(p, reviews);
        Review review = new Review(rat, comment);
        reviews.add(review);
        int sum = 0;

        for(var r : reviews){
            sum += r.getRating().ordinal();
        }
        int mediaStars = Math.round((float)sum/reviews.size());
//        System.out.println(mediaStars);
        p = p.applyRating(Rateable.convert(mediaStars));
//        System.out.println(p.getRating());
        products.put(p,reviews);
        return p;
    }

    public void printProductReportWithStreams(Product product)
    {

        StringBuilder txt =  new StringBuilder();
        Collections.sort(products.get(product));
        List<Review> reviews = products.get(product);
        txt.append(formatter.formatProduct(product));
        txt.append('\n');

        if(reviews.isEmpty()){
            txt.append(formatter.getText("no.reviews"));
            txt.append('\n');
        }else{
            txt.append(reviews.stream()
                              .map(r->formatter.formatReview(r) +'\n')
                              .collect(Collectors.joining()));
        }
        System.out.println(txt);
    }
    public void printProductReport(int id)
    {
        printProductReport(findProduct(id));
    }
    public void printProductReport(Product product)
    {
        StringBuilder txt =  new StringBuilder();
        Collections.sort(products.get(product));
        List<Review> reviews = products.get(product);
        txt.append(formatter.formatProduct(product));
        txt.append('\n');
        for(var review : reviews){
            txt.append(formatter.formatReview(review));
            txt.append('\n');
        }
        if(reviews.isEmpty()){
            txt.append(formatter.getText("no.reviews"));
            txt.append('\n');
        }
        System.out.println(txt);
    }
    public Product findProductWithStreams(int id)
    {
        return products.keySet()
                       .stream()
                        .filter(p ->p.getId() == id)
                         .findFirst()
                         .orElseGet(() -> null);
    }

    public Product findProduct(int id)
    {
          Product result = null;
        for(var p :  products.keySet()){
            if(p.getId() == id){
                result = p;
                break;
            }
        }
        return result;
    }
    public void printProductsWithStreams(Comparator<Product> sorter)
    {

        StringBuilder txt  =  new StringBuilder();
        products.keySet().stream().sorted(sorter).forEach(p -> txt.append(formatter.formatProduct(p)+'\n'));
        System.out.println(txt);
    }

    public void printProducts(Comparator<Product> sorter)
    {
        List<Product> productsList = new ArrayList<>(products.keySet());
        productsList.sort(sorter);

        StringBuilder txt =  new StringBuilder();
        for(var p : productsList)
        {
            txt.append(formatter.formatProduct(p));
            txt.append('\n');
        }
        System.out.println(txt);
    }

}
