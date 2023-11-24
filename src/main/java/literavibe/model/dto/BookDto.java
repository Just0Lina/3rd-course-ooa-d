package literavibe.model.dto;

import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * BookDto
 */
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-11-24T06:59:10.235174255Z[GMT]")


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

  public List<AuthorDto> getAuthors() {
    return authors;
  }

  public void setAuthors(List<AuthorDto> authors) {
    this.authors = authors;
  }

  @JsonProperty("authors")
  private List<AuthorDto> authors;

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
    BookDto bookDto = (BookDto) o;
    return Objects.equals(this.name, bookDto.name) &&
        Objects.equals(this.id, bookDto.id) &&
        Objects.equals(this.mediaId, bookDto.mediaId) &&
        Objects.equals(this.pages, bookDto.pages) &&
        Objects.equals(this.info, bookDto.info) &&
        Objects.equals(this.year, bookDto.year) &&
        Objects.equals(this.publisherId, bookDto.publisherId);
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
    sb.append("    authors: ").append(toIndentedString(authors)).append("\n");
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
