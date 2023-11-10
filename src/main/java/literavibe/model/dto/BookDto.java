package literavibe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

/**
 * BookDto
 */
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-09-21T03:26:12.977088839Z[GMT]")


public class BookDto   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("media_id")
  private Long mediaId = null;

  @JsonProperty("pages")
  private Integer pages = null;

  @JsonProperty("info")
  private String info = null;

//  @JsonProperty("author_id")
//  private String authorId = null;

  @JsonProperty("authors")
  private List<AuthorDto> authors;

  public List<AuthorDto> getAuthors() {
    return authors;
  }

  public void setAuthors(List<AuthorDto> authors) {
    this.authors = authors;
  }

  @JsonProperty("year")
  private Integer year = null;

  @JsonProperty("publisher_id")
  private Integer publisherId = null;

  public BookDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
      @NotNull

  @Size(max=128)   public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BookDto id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
      @NotNull

    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BookDto mediaId(Long mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  /**
   * Get mediaId
   * @return mediaId
   **/
      @NotNull

    public Long getMediaId() {
    return mediaId;
  }

  public void setMediaId(Long mediaId) {
    this.mediaId = mediaId;
  }

  public BookDto pages(Integer pages) {
    this.pages = pages;
    return this;
  }

  /**
   * Get pages
   * @return pages
   **/
  
    public Integer getPages() {
    return pages;
  }

  public void setPages(Integer pages) {
    this.pages = pages;
  }

  public BookDto info(String info) {
    this.info = info;
    return this;
  }

  /**
   * Get info
   * @return info
   **/
      @NotNull

  @Size(max=32)   public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }


  public BookDto year(Integer year) {
    this.year = year;
    return this;
  }

  /**
   * Get year
   * @return year
   **/
  
    public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public BookDto publisherId(Integer publisherId) {
    this.publisherId = publisherId;
    return this;
  }

  /**
   * Get publisherId
   * @return publisherId
   **/
      @NotNull

    public Integer getPublisherId() {
    return publisherId;
  }

  public void setPublisherId(Integer publisherId) {
    this.publisherId = publisherId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookDto booksDto = (BookDto) o;
    return Objects.equals(this.name, booksDto.name) &&
        Objects.equals(this.id, booksDto.id) &&
        Objects.equals(this.mediaId, booksDto.mediaId) &&
        Objects.equals(this.pages, booksDto.pages) &&
        Objects.equals(this.info, booksDto.info) &&
        Objects.equals(this.year, booksDto.year) &&
        Objects.equals(this.publisherId, booksDto.publisherId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id, mediaId, pages, info, year, publisherId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BookDto {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    mediaId: ").append(toIndentedString(mediaId)).append("\n");
    sb.append("    pages: ").append(toIndentedString(pages)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    year: ").append(toIndentedString(year)).append("\n");
    sb.append("    publisherId: ").append(toIndentedString(publisherId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
