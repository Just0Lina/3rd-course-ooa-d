package literavibe.model.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * AuthorDto
 */
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-11-10T09:07:36.123096787Z[GMT]")


public class AuthorDto   {
    @JsonProperty("first_name")
    private String firstName = null;

    @JsonProperty("second_name")
    private String secondName = null;

    @JsonProperty("info")
    private String info = null;

    @JsonProperty("image_url")
    private Long imageUrl = null;

    public AuthorDto firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Get firstName
     * @return firstName
     **/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public AuthorDto secondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    /**
     * Get secondName
     * @return secondName
     **/
    @NotNull

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public AuthorDto info(String info) {
        this.info = info;
        return this;
    }

    /**
     * Get info
     * @return info
     **/
    @NotNull

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public AuthorDto imageUrl(Long imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    /**
     * Get imageUrl
     * @return imageUrl
     **/

    public Long getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Long imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(this.firstName, authorDto.firstName) &&
                Objects.equals(this.secondName, authorDto.secondName) &&
                Objects.equals(this.info, authorDto.info) &&
                Objects.equals(this.imageUrl, authorDto.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, info, imageUrl);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthorDto {\n");

        sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
        sb.append("    secondName: ").append(toIndentedString(secondName)).append("\n");
        sb.append("    info: ").append(toIndentedString(info)).append("\n");
        sb.append("    imageUrl: ").append(toIndentedString(imageUrl)).append("\n");
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