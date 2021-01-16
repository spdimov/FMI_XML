package books.physical;

import books.Author;

public class PhysicalBook {

    String id;
    String title;
    Author author;
    int releaseYear;
    String genre;
    String publisher;
    String language;
    double price;
    double rating;
    int length;
    int width;
    int pagesNumber;
    String coversType;


    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(String name, String nationality) {
        this.author = new Author(name, nationality);
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public void setCoversType(String coversType) {
        this.coversType = coversType;
    }

    @Override
    public String toString() {
        return "PhysicalBook{" +
               "title='" + title + '\'' +
               ", author=" + author +
               ", releaseYear=" + releaseYear +
               ", genre='" + genre + '\'' +
               ", publisher='" + publisher + '\'' +
               ", language='" + language + '\'' +
               ", price=" + price +
               ", rating=" + rating +
               ", length=" + length +
               ", width=" + width +
               ", pagesNumber=" + pagesNumber +
               ", coversType='" + coversType + '\'' +
               '}';
    }

    public String parseToDigitalBookXML() {
        final String DEFAULT_TITLE_LANG = "en";
        final String NO_INFO = "NO_INFO";

        String digitalBookBody = """
                <book id="%s">
                    <title lang="%s">%s</title>
                    <price>%2.1f</price>
                    <release_date>
                        <year>%d</year>
                    </release_date>
                    <author nationality="%s">%s</author>
                    <genre>%s</genre>
                    <publisher country="%s">%s</publisher>
                    <pages_number edition="%s">%d</pages_number>
                    <rating>%2.1f</rating>
                </book>""";

        return String.format(digitalBookBody,
                id,
                DEFAULT_TITLE_LANG,
                title,
                price,
                releaseYear,
                author.getNationality(),
                author.getName(),
                genre,
                NO_INFO,
                publisher,
                language,
                pagesNumber,
                rating);
    }
}
