/*
 * Copyright (c) 2010 Mark Allen.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.restfb.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.restfb.Facebook;

/**
 * Represents the <a
 * href="http://developers.facebook.com/docs/reference/api/post">Post Graph API
 * type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 * @since 1.5
 */
public class Post extends NamedFacebookType {
  @Facebook
  private CategorizedFacebookType from;

  @Facebook
  private String message;

  @Facebook
  private String picture;

  @Facebook
  private String link;

  @Facebook
  private String caption;

  @Facebook
  private String description;

  @Facebook
  private String source;

  @Facebook
  private String icon;

  @Facebook
  private String attribution;

  @Facebook
  private Privacy privacy;

  @Facebook
  private Long likes;

  @Facebook("created_time")
  private String createdTime;

  @Facebook("updated_time")
  private String updatedTime;

  @Facebook
  private Comments comments;

  @Facebook(contains = NamedFacebookType.class)
  private List<NamedFacebookType> to = new ArrayList<NamedFacebookType>();

  @Facebook(contains = Action.class)
  private List<Action> actions = new ArrayList<Action>();

  /**
   * Represents a collection of {@link Comment}s.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5.3
   */
  public static class Comments {
    @Facebook
    private Long count;

    @Facebook(contains = Comment.class)
    private List<Comment> data = new ArrayList<Comment>();

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }

    /**
     * The number of comments.
     * 
     * @return The number of comments.
     */
    public Long getCount() {
      return count;
    }

    /**
     * The comments.
     * 
     * @return The comments.
     */
    public List<Comment> getData() {
      return Collections.unmodifiableList(data);
    }
  }

  /**
   * Represents the <a
   * href="http://developers.facebook.com/docs/reference/api/post">Privacy Graph
   * API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5
   */
  public static class Privacy {
    @Facebook
    private String value;

    @Facebook
    private String description;

    @Facebook
    private String friends;

    @Facebook
    private String networks;

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }

    /**
     * The description of the privacy value.
     * 
     * @return The description of the privacy value.
     */
    public String getValue() {
      return value;
    }

    /**
     * The privacy description.
     * 
     * @return The privacy description.
     */
    public String getDescription() {
      return description;
    }

    /**
     * The privacy friends restriction.
     * 
     * @return The privacy friends restriction.
     */
    public String getFriends() {
      return friends;
    }

    /**
     * The privacy networks restriction.
     * 
     * @return The privacy networks restriction.
     */
    public String getNetworks() {
      return networks;
    }
  }

  /**
   * Represents the <a
   * href="http://developers.facebook.com/docs/reference/api/post">Action Graph
   * API type</a>.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.5
   */
  public static class Action {
    @Facebook
    private String name;

    @Facebook
    private String link;

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
      return ReflectionUtils.hashCode(this);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object that) {
      return ReflectionUtils.equals(this, that);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      return ReflectionUtils.toString(this);
    }

    /**
     * Gets the name of the action.
     * 
     * @return Gets the name of the action.
     */
    public String getName() {
      return name;
    }

    /**
     * The link for the action.
     * 
     * @return The link for the action.
     */
    public String getLink() {
      return link;
    }
  }

  /**
   * An object containing the ID and name of the user who posted the message.
   * 
   * @return An object containing the ID and name of the user who posted the
   *         message.
   */
  public CategorizedFacebookType getFrom() {
    return from;
  }

  /**
   * The message.
   * 
   * @return The message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * If available, a link to the picture included with this post.
   * 
   * @return If available, a link to the picture included with this post.
   */
  public String getPicture() {
    return picture;
  }

  /**
   * The link attached to this post.
   * 
   * @return The link attached to this post.
   */
  public String getLink() {
    return link;
  }

  /**
   * The caption of the link (appears beneath the link name).
   * 
   * @return The caption of the link (appears beneath the link name).
   */
  public String getCaption() {
    return caption;
  }

  /**
   * A description of the link (appears beneath the link caption).
   * 
   * @return A description of the link (appears beneath the link caption).
   */
  public String getDescription() {
    return description;
  }

  /**
   * If available, the source link attached to this post (for example, a flash
   * or video file).
   * 
   * @return If available, the source link attached to this post (for example, a
   *         flash or video file).
   */
  public String getSource() {
    return source;
  }

  /**
   * A link to an icon representing the type of this post.
   * 
   * @return A link to an icon representing the type of this post.
   */
  public String getIcon() {
    return icon;
  }

  /**
   * A string indicating which application was used to create this post.
   * 
   * @return A string indicating which application was used to create this post.
   */
  public String getAttribution() {
    return attribution;
  }

  /**
   * The privacy settings for this post.
   * 
   * @return The privacy settings for this post.
   */
  public Privacy getPrivacy() {
    return privacy;
  }

  /**
   * The number of likes on this post.
   * 
   * @return The number of likes on this post.
   */
  public Long getLikes() {
    return likes;
  }

  /**
   * The time the post was initially published.
   * 
   * @return The time the post was initially published.
   */
  public Date getCreatedTime() {
    return StringUtils.toDate(createdTime);
  }

  /**
   * The time of the last comment on this post.
   * 
   * @return The time of the last comment on this post.
   */
  public Date getUpdatedTime() {
    return StringUtils.toDate(updatedTime);
  }

  /**
   * The comments for this post.
   * 
   * @return The comments for this post.
   */
  public Comments getComments() {
    return comments;
  }

  /**
   * A list of the profiles mentioned or targeted in this post.
   * 
   * @return A list of the profiles mentioned or targeted in this post.
   */
  public List<NamedFacebookType> getTo() {
    return Collections.unmodifiableList(to);
  }

  /**
   * A list of available action names and links (including commenting, liking,
   * and an optional app-specified action).
   * 
   * @return A list of available action names and links (including commenting,
   *         liking, and an optional app-specified action).
   */
  public List<Action> getActions() {
    return Collections.unmodifiableList(actions);
  }
}