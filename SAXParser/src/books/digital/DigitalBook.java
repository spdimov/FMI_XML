package books.digital;

import books.Author;
import books.Publisher;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class DigitalBook {

    String id;
    String title;
    double price;
    Calendar releaseDate;
    Author author;
    String genre;
    Publisher publisher;
    int pagesNumber;
    String edition;
    Set<String> formats = new HashSet<>();
    int size;
    String review;
    double rating;
    int minimumAge;
    String titleLang;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public Set<String> getFormats() {
        return formats;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public void setTitleLang(String titleLang) {
        this.titleLang = titleLang;
    }


    public void setAuthor(String name, String nationality) {
        author = new Author(name, nationality);
    }


    public void setPublisher(String name, String nationality) {
        publisher = new Publisher(name, nationality);
    }


    @Override
    public String toString() {
        return "DigitalBook{" +
               "title='" + title + '\'' +
               ", price=" + price +
               ", releaseDate=" + releaseDate +
               ", author=" + author +
               ", genre='" + genre + '\'' +
               ", publisher=" + publisher +
               ", pagesNumber=" + pagesNumber +
               ", formats=" + formats +
               ", size=" + size +
               ", review='" + review + '\'' +
               ", titleLang='" + titleLang + '\'' +
               ", rating=" + rating +
               ", minimumAge=" + minimumAge +
               ", edition='" + edition + '\'' +
               '}';
    }

    public String parseToPhysicalBookXML() {
        final Double DIGITAL_DEFAULT_DIMENSION = 0.0;
        final String NO_INFO = "NO_INFO";

        String physicalBookBody = """
                \t<book>
                \t\t<id>%s</id>
                \t\t<title>%s</title>
                \t\t<author nationality = "%s">%s</author>
                \t\t<year>%s</year>
                \t\t<genre>%s</genre>\t\t
                \t\t<publisher>\t%s</publisher>
                \t\t<language>%s</language>
                \t\t<price currency="%s">%2.1f</price>
                \t\t<rating>%2.1f</rating>\t\t
                \t\t<dimensions unit="%s">
                \t\t\t<length>%2.1f</length>
                \t\t\t<width>%2.1f</width>
                \t\t</dimensions>
                \t\t<pages>%d</pages>
                \t\t<covers>%s</covers>
                \t</book>""";

        return String.format(physicalBookBody,
                id,
                title,
                author.getNationality(),
                author.getName(),
                releaseDate.get(Calendar.YEAR),
                genre,
                publisher.getName(),
                edition,
                "EUR",
                price,
                rating,
                NO_INFO,
                DIGITAL_DEFAULT_DIMENSION,
                DIGITAL_DEFAULT_DIMENSION,
                pagesNumber,
                NO_INFO);
    }
}
