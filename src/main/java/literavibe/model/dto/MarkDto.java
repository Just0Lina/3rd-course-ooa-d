package literavibe.model.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * MarkDto
 */
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-09-21T03:26:12.977088839Z[GMT]")


public class MarkDto   {
  @JsonProperty("login")
  private String login = null;

  @JsonProperty("book_id")
  private Long bookId = null;

  @JsonProperty("mark")
  private Integer mark = null;

  public MarkDto userId(String userId) {
    this.login = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/
      @NotNull

  @Size(max=32)   public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public MarkDto bookId(Long bookId) {
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

  public MarkDto mark(Integer mark) {
    this.mark = mark;
    return this;
  }

  /**
   * Оценка от 1 до 5
   * @return mark
   **/
      @NotNull

    public Integer getMark() {
    return mark;
  }

  public void setMark(Integer mark) {
    this.mark = mark;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MarkDto markDto = (MarkDto) o;
    return Objects.equals(this.login, markDto.login) &&
        Objects.equals(this.bookId, markDto.bookId) &&
        Objects.equals(this.mark, markDto.mark);
  }

  @Override
  public int hashCode() {
    return Objects.hash(login, bookId, mark);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MarkDto {\n");
    
    sb.append("    userId: ").append(toIndentedString(login)).append("\n");
    sb.append("    bookId: ").append(toIndentedString(bookId)).append("\n");
    sb.append("    mark: ").append(toIndentedString(mark)).append("\n");
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
