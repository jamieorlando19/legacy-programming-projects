package com.warthog.application.controllers;

public class SignUpBean
{
  String complaints;

  String bandname;
  String url;
  String contname;
  String contaddr;
  String contcity;
  String contstate;
  String contzip;
  String contphone;
  String contemail;
  String password;
  String pwconfirm;
  String chatroom;
  String messboard;
  String layout;
  String ccnumber;
  String ccname;
  String expmonth;
  String expyear;
  String cardtype;


  public SignUpBean() {
    complaints = new String("");
    bandname = new String("");
    url = new String("");
    contname = new String("");
    contaddr = new String("");
    contcity = new String("");
    contstate = new String("");
    contzip = new String("");
    contphone = new String("");
    contemail = new String("");
    password = new String("");
    pwconfirm = new String("");
    chatroom = new String("");
    messboard = new String("");
    layout = new String("");
    ccnumber = new String("");
    ccname = new String("");
    expmonth = new String("");
    expyear = new String("");
    cardtype = new String("");
  }


  public String getComplaints() {
    return complaints;
  }
  public void setComplaints( String c ) {
    if( c != null )
      complaints = c;
  }


  public String getBandname() {
    return bandname;
  }
  public void setBandname( String b ) {
    if( b != null )
      bandname = b;
  }

  public String getUrl() {
    return url;
  }
  public void setUrl( String u ) {
    if( u != null )
      url = u;
  }

  public String getContname() {
    return contname;
  }
  public void setContname( String c ) {
    if( c != null )
      contname = c;
  }

  public String getContaddr() {
    return contaddr;
  }
  public void setContaddr( String c ) {
    if( c != null )
      contaddr = c;
  }

  public String getContcity() {
    return contcity;
  }
  public void setContcity( String c ) {
    if( c != null )
      contcity = c;
  }

  public String getContstate() {
    return contstate;
  }
  public void setContstate( String c ) {
    if( c != null )
      contstate = c;
  }

  public String getContzip() {
    return contzip;
  }
  public void setContzip( String c ) {
    if( c != null )
      contzip = c;
  }

  public String getContphone() {
    return contphone;
  }
  public void setContphone( String c ) {
    if( c != null )
      contphone = c;
  }

  public String getContemail() {
    return contemail;
  }
  public void setContemail( String c ) {
    if( c != null )
      contemail = c;
  }

  public String getPassword() {
    return password;
  }
  public void setPassword( String p ) {
    if( p != null )
      password = p;
  }

  public String getPwconfirm() {
    return pwconfirm;
  }
  public void setPwconfirm( String p ) {
    if( p != null )
      pwconfirm = p;
  }

  public String getChatroom() {
    return chatroom;
  }
  public void setChatroom( String c ) {
    if( c != null )
      chatroom = c;
  }

  public String getMessboard() {
    return messboard;
  }
  public void setMessboard( String m ) {
    if( m != null )
      messboard = m;
  }

  public String getLayout() {
    return layout;
  }
  public void setLayout( String l ) {
    if( l != null )
      layout = l;
  }

  public String getCcnumber() {
    return ccnumber;
  }
  public void setCcnumber( String c ) {
    if( c != null )
      ccnumber = c;
  }

  public String getCcname() {
    return ccname;
  }
  public void setCcname( String c ) {
    if( c != null )
      ccname = c;
  }

  public String getExpmonth() {
    return expmonth;
  }
  public void setExpmonth( String e ) {
    if( e != null )
      expmonth = e;
  }

  public String getExpyear() {
    return expyear;
  }
  public void setExpyear( String e ) {
    if( e != null )
      expyear = e;
  }

  public String getCardtype() {
    return cardtype;
  }
  public void setCardtype( String c ) {
    if( c != null )
      cardtype = c;
  }

}
