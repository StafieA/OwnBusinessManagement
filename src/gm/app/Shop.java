package gm.app;

import gm.data.*;

import static gm.data.Rating.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Locale;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Shop {
    public static void main(String[] args) {



        ProductManager pm = new ProductManager(Locale.JAPAN);
        System.out.println(ProductManager.getSupportedLocales());

        System.out.println('\n');
        pm.createProduct(101,"Tea",BigDecimal.valueOf(34.6), NOT_RATED);
        pm.printProductReport(101);
        pm.reviewProduct(101, FOUR_STAR,"Nice hot cup of tea");
        pm.reviewProduct(101, TWO_STAR,"Rather weak tea");
        pm.reviewProduct(101, FOUR_STAR,"Fine tea");
        pm.reviewProduct(101, FOUR_STAR,"Good tea");
        pm.reviewProduct(101, FIVE_STAR,"Perfect tea");
        pm.reviewProduct(101, THREE_STAR,"Just add some lemon");
        pm.printProductReport(101);

//        pm.changeLocale("de-DE");

        pm.createProduct(102,"Coffee",BigDecimal.valueOf(16.5), NOT_RATED);
        pm.printProductReport(102);
        pm.reviewProduct(102, THREE_STAR,"Coffee was ok");
        pm.reviewProduct(102, ONE_STAR,"Where is the milk");
        pm.reviewProduct(102, FIVE_STAR,"It s perfect with 10 spoons of sugar");
        pm.printProductReport(102);

        pm.createProduct(103,"Cake",BigDecimal.valueOf(3.99), NOT_RATED,LocalDate.now().plusDays(4));
        pm.printProductReport(103);
        pm.reviewProduct(103, FIVE_STAR,"Very nice cake");
        pm.reviewProduct(103, FOUR_STAR,"It s good but I ve expected more chocolate");
        pm.reviewProduct(103, FIVE_STAR,"This cake is perfect");
        pm.printProductReport(103);

        pm.createProduct(104,"Cookie",BigDecimal.valueOf(3.99), NOT_RATED,LocalDate.now());
        pm.printProductReport(104);
        pm.reviewProduct(104, THREE_STAR,"Just another cookie");
        pm.reviewProduct(104, THREE_STAR,"Ok");
        pm.printProductReport(104);

        pm.createProduct(105,"Hot Chocolate",BigDecimal.valueOf(9.88), NOT_RATED);
        pm.printProductReport(105);
        pm.reviewProduct(105, FOUR_STAR,"Tasty !");
        pm.reviewProduct(105, FOUR_STAR,"Not bad at all");
        pm.printProductReport(105);

        pm.createProduct(106,"Chocolate",BigDecimal.valueOf(6.0), NOT_RATED,LocalDate.now().plusDays(3));
        pm.printProductReport(106);
        pm.reviewProduct(106, TWO_STAR,"Too sweet");
        pm.reviewProduct(106, THREE_STAR,"Better than cookie");
        pm.reviewProduct(106, TWO_STAR,"Two bitter");
        pm.reviewProduct(106, ONE_STAR,"I don't get it");

        pm.printProductReport(106);
        System.out.println("===============Products compared by RATING==================");
        Comparator<Product> ascSortRating = (p1,p2)->p1.getRating().ordinal() - p2.getRating().ordinal();
        Comparator<Product> descSortRating = (p1,p2)->p2.getRating().ordinal() - p1.getRating().ordinal();
        System.out.println("===========ASCENDING===========");
        pm.printProducts(ascSortRating);
        System.out.println("===========DESCENDING===========");
        pm.printProducts(descSortRating);
        System.out.println("===============Products compared by PRICE==================");
        Comparator<Product> ascSortPrice = (p1,p2)->p1.getPrice().compareTo( p2.getPrice());
        Comparator<Product> descSortPrice= (p1,p2)->p2.getPrice().compareTo( p1.getPrice());
        System.out.println("===========ASCENDING===========");
        pm.printProducts(ascSortPrice);
        System.out.println("===========DESCENDING===========");
        pm.printProducts(descSortPrice);
        System.out.println("===============Products compared by NAME==================");
        Comparator<Product> ascSortName = (p1,p2)->p1.getName().compareTo( p2.getName()) ;
        Comparator<Product> descSortName= (p1,p2)->p2.getName().compareTo( p1.getName()) ;
        System.out.println("===========ASCENDING===========");
        pm.printProducts(ascSortName);
        System.out.println("===========DESCENDING===========");
        pm.printProducts(descSortName);
        System.out.println("===============Products compared by BEST-BEFORE==================");
        Comparator<Product> ascSortBestBefore = (p1,p2)->p1.getBestBefore().compareTo( p2.getBestBefore()) ;
        Comparator<Product> descSortBestBefore= (p1,p2)->p2.getBestBefore().compareTo( p1.getBestBefore()) ;
        System.out.println("===========ASCENDING===========");
        pm.printProducts(ascSortBestBefore);
        System.out.println("===========DESCENDING===========");
        pm.printProducts(descSortBestBefore);



    }
}