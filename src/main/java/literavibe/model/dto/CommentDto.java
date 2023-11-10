package literavibe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * CommentDto
 */
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-09-21T03:26:12.977088839Z[GMT]")


public class CommentDto   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("login")
  private String login = null;

  @JsonProperty("book_id")
  private Long bookId = null;

  @JsonProperty("post_time")
  private OffsetDateTime postTime = null;

  @JsonProperty("content")
  private String content = null;

  public CommentDto id(Long id) {
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

  public CommentDto userId(String userId) {
    this.login = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/
      @NotNull

    public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public CommentDto bookId(Long bookId) {
    this.bookId = bookId;
    return this;
  }

  /**
   * Get bookId
   * @return bookId
   **/
      @NotNull

    public Long getBookId() {
    return bookId;
  }

  public void setBookId(Long bookId) {
    this.bookId = bookId;
  }

  public CommentDto postTime(OffsetDateTime postTime) {
    this.postTime = postTime;
    return this;
  }

  /**
   * Timestamp defined by RFC3339(ISO 8601)
   * @return postTime
   **/
  
    @Valid
    public OffsetDateTime getPostTime() {
    return postTime;
  }

  public void setPostTime(OffsetDateTime postTime) {
    this.postTime = postTime;
  }

  public CommentDto content(String content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
   **/
      @NotNull

    public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentDto commentDto = (CommentDto) o;
    return Objects.equals(this.id, commentDto.id) &&
        Objects.equals(this.login, commentDto.login) &&
        Objects.equals(this.bookId, commentDto.bookId) &&
        Objects.equals(this.postTime, commentDto.postTime) &&
        Objects.equals(this.content, commentDto.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, login, bookId, postTime, content);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommentDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    userId: ").append(toIndentedString(login)).append("\n");
    sb.append("    bookId: ").append(toIndentedString(bookId)).append("\n");
    sb.append("    postTime: ").append(toIndentedString(postTime)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
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
