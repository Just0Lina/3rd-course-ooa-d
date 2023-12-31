package literavibe.model.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.*;

/**
 * UserProfileDto
 */
@Validated
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-07-27T07:34:19.182164167Z[GMT]")


public class UserProfileDto   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("display_name")
  private String displayName = null;

  @JsonProperty("media_id")
  private Long mediaId = null;

  @JsonProperty("info")
  private String info = null;

  @JsonProperty("tg_link")
  private String tgLink = null;

  @JsonProperty("vk_link")
  private String vkLink = null;

  public UserProfileDto id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UserProfileDto displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  /**
   * Get displayName
   * @return displayName
   **/
      @NotNull

    public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public UserProfileDto mediaId(Long mediaId) {
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

  public UserProfileDto info(String info) {
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

  public UserProfileDto tgLink(String tgLink) {
    this.tgLink = tgLink;
    return this;
  }

  /**
   * Get tgLink
   * @return tgLink
   **/

    public String getTgLink() {
    return tgLink;
  }

  public void setTgLink(String tgLink) {
    this.tgLink = tgLink;
  }

  public UserProfileDto vkLink(String vkLink) {
    this.vkLink = vkLink;
    return this;
  }

  /**
   * Get vkLink
   * @return vkLink
   **/

    public String getVkLink() {
    return vkLink;
  }

  public void setVkLink(String vkLink) {
    this.vkLink = vkLink;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserProfileDto userProfileDto = (UserProfileDto) o;
    return Objects.equals(this.id, userProfileDto.id) &&
        Objects.equals(this.displayName, userProfileDto.displayName) &&
        Objects.equals(this.mediaId, userProfileDto.mediaId) &&
        Objects.equals(this.info, userProfileDto.info) &&
        Objects.equals(this.tgLink, userProfileDto.tgLink) &&
        Objects.equals(this.vkLink, userProfileDto.vkLink);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, displayName, mediaId, info, tgLink, vkLink);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserProfileDto {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    mediaId: ").append(toIndentedString(mediaId)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    tgLink: ").append(toIndentedString(tgLink)).append("\n");
    sb.append("    vkLink: ").append(toIndentedString(vkLink)).append("\n");
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
