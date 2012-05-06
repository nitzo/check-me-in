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

import java.util.Date;

import com.restfb.Facebook;

/**
 * Extension of RestFB
 * Represents an Open Authentication 2.0 object
 * 
 * Use this to store object's access token
 * 
 */
public class OAuth extends FacebookType {
  @Facebook("access_token")
  private String accessToken;

  @Facebook
  private int expires;

  
  public OAuth(){
	  this.accessToken = "";
	  this.expires = 0; //Set Expires
  }
  
  
  /**
   * The access token of the Object
   * 
   * @return A string representing the access token of the object
   */
  public String getAccessToken() {
    return accessToken;
  }

  /**
   * 
   * 
   * @return An integer representing when the access token will epire. 0 == No expiry
   */
  public int getExpires() {
    return expires;
  }
  
}