/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */
/*
Copyright (c) 2002-2012 ymnk, JCraft,Inc. All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

  1. Redistributions of source code must retain the above copyright notice,
     this list of conditions and the following disclaimer.

  2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in
     the documentation and/or other materials provided with the distribution.

  3. The names of the authors may not be used to endorse or promote products
     derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JCRAFT,
INC. OR ANY CONTRIBUTORS TO THIS SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.intellivision.util.ssh;

import java.io.InputStream;
import java.util.Vector;

public class SSHConnection{
  /**
   * The version number.
   */
  public static final String VERSION  = "0.1.49";

  static java.util.Hashtable config=new java.util.Hashtable();
  static{
    config.put("kex", "diffie-hellman-group1-sha1,diffie-hellman-group14-sha1,diffie-hellman-group-exchange-sha1");
    config.put("server_host_key", "ssh-rsa,ssh-dss");

    config.put("cipher.s2c",
               "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-cbc,aes256-cbc");
    config.put("cipher.c2s",
               "aes128-ctr,aes128-cbc,3des-ctr,3des-cbc,blowfish-cbc,aes192-cbc,aes256-cbc");

    config.put("mac.s2c", "hmac-md5,hmac-sha1,hmac-sha2-256,hmac-sha1-96,hmac-md5-96");
    config.put("mac.c2s", "hmac-md5,hmac-sha1,hmac-sha2-256,hmac-sha1-96,hmac-md5-96");
    config.put("compression.s2c", "none");
    config.put("compression.c2s", "none");

    config.put("lang.s2c", "");
    config.put("lang.c2s", "");

    config.put("compression_level", "6");

    config.put("diffie-hellman-group-exchange-sha1",
                                "com.intellivision.util.ssh.DHGEX");
    config.put("diffie-hellman-group1-sha1",
	                        "com.intellivision.util.ssh.DHG1");
    config.put("diffie-hellman-group14-sha1",
	                        "com.intellivision.util.ssh.DHG14");

    config.put("dh",            "com.intellivision.util.ssh.jce.DH");
    config.put("3des-cbc",      "com.intellivision.util.ssh.jce.TripleDESCBC");
    config.put("blowfish-cbc",  "com.intellivision.util.ssh.jce.BlowfishCBC");
    config.put("hmac-sha1",     "com.intellivision.util.ssh.jce.HMACSHA1");
    config.put("hmac-sha1-96",  "com.intellivision.util.ssh.jce.HMACSHA196");
    config.put("hmac-sha2-256",  "com.intellivision.util.ssh.jce.HMACSHA256");
    // The "hmac-sha2-512" will require the key-length 2048 for DH,
    // but Sun's JCE has not allowed to use such a long key.
    //config.put("hmac-sha2-512",  "com.intellivision.util.ssh.jce.HMACSHA512");
    config.put("hmac-md5",      "com.intellivision.util.ssh.jce.HMACMD5");
    config.put("hmac-md5-96",   "com.intellivision.util.ssh.jce.HMACMD596");
    config.put("sha-1",         "com.intellivision.util.ssh.jce.SHA1");
    config.put("md5",           "com.intellivision.util.ssh.jce.MD5");
    config.put("signature.dss", "com.intellivision.util.ssh.jce.SignatureDSA");
    config.put("signature.rsa", "com.intellivision.util.ssh.jce.SignatureRSA");
    config.put("keypairgen.dsa",   "com.intellivision.util.ssh.jce.KeyPairGenDSA");
    config.put("keypairgen.rsa",   "com.intellivision.util.ssh.jce.KeyPairGenRSA");
    config.put("random",        "com.intellivision.util.ssh.jce.Random");

    config.put("none",           "com.intellivision.util.ssh.CipherNone");

    config.put("aes128-cbc",    "com.intellivision.util.ssh.jce.AES128CBC");
    config.put("aes192-cbc",    "com.intellivision.util.ssh.jce.AES192CBC");
    config.put("aes256-cbc",    "com.intellivision.util.ssh.jce.AES256CBC");

    config.put("aes128-ctr",    "com.intellivision.util.ssh.jce.AES128CTR");
    config.put("aes192-ctr",    "com.intellivision.util.ssh.jce.AES192CTR");
    config.put("aes256-ctr",    "com.intellivision.util.ssh.jce.AES256CTR");
    config.put("3des-ctr",      "com.intellivision.util.ssh.jce.TripleDESCTR");
    config.put("arcfour",      "com.intellivision.util.ssh.jce.ARCFOUR");
    config.put("arcfour128",      "com.intellivision.util.ssh.jce.ARCFOUR128");
    config.put("arcfour256",      "com.intellivision.util.ssh.jce.ARCFOUR256");

    config.put("userauth.none",    "com.intellivision.util.ssh.UserAuthNone");
    config.put("userauth.password",    "com.intellivision.util.ssh.UserAuthPassword");
    config.put("userauth.keyboard-interactive",    "com.intellivision.util.ssh.UserAuthKeyboardInteractive");
    config.put("userauth.publickey",    "com.intellivision.util.ssh.UserAuthPublicKey");
    config.put("userauth.gssapi-with-mic",    "com.intellivision.util.ssh.UserAuthGSSAPIWithMIC");
    config.put("gssapi-with-mic.krb5",    "com.intellivision.util.ssh.jgss.GSSContextKrb5");

    config.put("zlib",             "com.intellivision.util.ssh.jcraft.Compression");
    config.put("zlib@openssh.com", "com.intellivision.util.ssh.jcraft.Compression");

    config.put("StrictHostKeyChecking",  "ask");
    config.put("HashKnownHosts",  "no");

    config.put("PreferredAuthentications", "gssapi-with-mic,publickey,keyboard-interactive,password");

    config.put("CheckCiphers", "aes256-ctr,aes192-ctr,aes128-ctr,aes256-cbc,aes192-cbc,aes128-cbc,3des-ctr,arcfour,arcfour128,arcfour256");
    config.put("CheckKexes", "diffie-hellman-group14-sha1");

    config.put("MaxAuthTries", "6");
  }

  private java.util.Vector sessionPool = new java.util.Vector();

  private IdentityRepository defaultIdentityRepository =
    new LocalIdentityRepository(this);

  private IdentityRepository identityRepository = defaultIdentityRepository;

  /**
   * Sets the <code>identityRepository</code>, which will be referred
   * in the public key authentication.
   *
   * @param identityRepository if <code>null</code> is given,
   * the default repository, which usually refers to ~/.ssh/, will be used.
   *
   * @see #getIdentityRepository()
   */
  public synchronized void setIdentityRepository(IdentityRepository identityRepository){
    if(identityRepository == null){
      this.identityRepository = defaultIdentityRepository;
    }
    else{
      this.identityRepository = identityRepository;
    }
  }

  synchronized IdentityRepository getIdentityRepository(){
    return this.identityRepository;
  }

  private HostKeyRepository known_hosts=null;

  private static final Logger DEVNULL=new Logger(){
      public boolean isEnabled(int level){return false;}
      public void log(int level, String message){}
    };
  static Logger logger=DEVNULL;

  public SSHConnection(){

    try{
      String osname=(String)(System.getProperties().get("os.name"));
      if(osname!=null && osname.equals("Mac OS X")){
        config.put("hmac-sha1",     "com.intellivision.util.ssh.jcraft.HMACSHA1");
        config.put("hmac-md5",      "com.intellivision.util.ssh.jcraft.HMACMD5");
        config.put("hmac-md5-96",   "com.intellivision.util.ssh.jcraft.HMACMD596");
        config.put("hmac-sha1-96",  "com.intellivision.util.ssh.jcraft.HMACSHA196");
      }
    }
    catch(Exception e){
    }

  }

  /**
   * Instantiates the <code>Session</code> object with
   * <code>username</code> and <code>host</code>.
   * The TCP port 22 will be used in making the connection.
   * Note that the TCP connection must not be established
   * until Session#connect().
   *
   * @param username user name
   * @param host hostname
   *
   * @throws SSHException
   *         if <code>username</code> or <code>host</code> are invalid.
   *
   * @return the instance of <code>Session</code> class.
   *
   * @see #getSession(String username, String host, int port)
   * @see com.intellivision.util.ssh.Session
   */
  public Session getSession(String username, String host)
     throws SSHException {
    return getSession(username, host, 22);
  }

  /**
   * Instantiates the <code>Session</code> object with given
   * <code>username</code>, <code>host</code> and <code>port</code>.
   * Note that the TCP connection must not be established
   * until Session#connect().
   *
   * @param username user name
   * @param host hostname
   * @param post port number
   *
   * @throws SSHException
   *         if <code>username</code> or <code>host</code> are invalid.
   *
   * @return the instance of <code>Session</code> class.
   *
   * @see #getSession(String username, String host, int port)
   * @see com.intellivision.util.ssh.Session
   */
  public Session getSession(String username, String host, int port) throws SSHException {
    if(username==null){
      throw new SSHException("username must not be null.");
    }
    if(host==null){
      throw new SSHException("host must not be null.");
    }
    Session s=new Session(this);
    s.setUserName(username);
    s.setHost(host);
    s.setPort(port);
    return s;
  }

  protected void addSession(Session session){
    synchronized(sessionPool){
      sessionPool.addElement(session);
    }
  }

  protected boolean removeSession(Session session){
    synchronized(sessionPool){
      return sessionPool.remove(session);
    }
  }

  /**
   * Sets the hostkey repository.
   *
   * @param hkrepo
   *
   * @see com.intellivision.util.ssh.HostKeyRepository
   * @see com.intellivision.util.ssh.KnownHosts
   */
  public void setHostKeyRepository(HostKeyRepository hkrepo){
    known_hosts=hkrepo;
  }

  /**
   * Sets the instance of <code>KnownHosts</code>, which refers
   * to <code>filename</code>.
   *
   * @param filename filename of known_hosts file.
   *
   * @throws SSHException
   *         if the given filename is invalid.
   *
   * @see com.intellivision.util.ssh.KnownHosts
   */
  public void setKnownHosts(String filename) throws SSHException{
    if(known_hosts==null) known_hosts=new KnownHosts(this);
    if(known_hosts instanceof KnownHosts){
      synchronized(known_hosts){
	((KnownHosts)known_hosts).setKnownHosts(filename);
      }
    }
  }

  /**
   * Sets the instance of <code>KnownHosts</code> generated with
   * <code>stream</code>.
   *
   * @param stream the instance of InputStream from known_hosts file.
   *
   * @throws SSHException
   *         if an I/O error occurs.
   *
   * @see com.intellivision.util.ssh.KnownHosts
   */
  public void setKnownHosts(InputStream stream) throws SSHException{
    if(known_hosts==null) known_hosts=new KnownHosts(this);
    if(known_hosts instanceof KnownHosts){
      synchronized(known_hosts){
	((KnownHosts)known_hosts).setKnownHosts(stream);
      }
    }
  }

  /**
   * Returns the current hostkey repository.
   * By the default, this method will the instance of <code>KnownHosts</code>.
   *
   * @return current hostkey repository.
   *
   * @see com.intellivision.util.ssh.HostKeyRepository
   * @see com.intellivision.util.ssh.KnownHosts
   */
  public HostKeyRepository getHostKeyRepository(){
    if(known_hosts==null) known_hosts=new KnownHosts(this);
    return known_hosts;
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   *
   * @param prvkey filename of the private key.
   *
   * @throws SSHException if <code>prvkey</code> is invalid.
   *
   * @see #addIdentity(String prvkey, String passphrase)
   */
  public void addIdentity(String prvkey) throws SSHException{
    addIdentity(prvkey, (byte[])null);
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   * Before registering it into identityRepository,
   * it will be deciphered with <code>passphrase</code>.
   *
   * @param prvkey filename of the private key.
   * @param passphrase passphrase for <code>prvkey</code>.
   *
   * @throws SSHException if <code>passphrase</code> is not right.
   *
   * @see #addIdentity(String prvkey, byte[] passphrase)
   */
  public void addIdentity(String prvkey, String passphrase) throws SSHException{
    byte[] _passphrase=null;
    if(passphrase!=null){
      _passphrase=Util.str2byte(passphrase);
    }
    addIdentity(prvkey, _passphrase);
    if(_passphrase!=null)
      Util.bzero(_passphrase);
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   * Before registering it into identityRepository,
   * it will be deciphered with <code>passphrase</code>.
   *
   * @param prvkey filename of the private key.
   * @param passphrase passphrase for <code>prvkey</code>.
   *
   * @throws SSHException if <code>passphrase</code> is not right.
   *
   * @see #addIdentity(String prvkey, String pubkey, byte[] passphrase)
   */
  public void addIdentity(String prvkey, byte[] passphrase) throws SSHException{
    Identity identity=IdentityFile.newInstance(prvkey, null, this);
    addIdentity(identity, passphrase);
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   * Before registering it into identityRepository,
   * it will be deciphered with <code>passphrase</code>.
   *
   * @param prvkey filename of the private key.
   * @param pubkey filename of the public key.
   * @param passphrase passphrase for <code>prvkey</code>.
   *
   * @throws SSHException if <code>passphrase</code> is not right.
   */
  public void addIdentity(String prvkey, String pubkey, byte[] passphrase) throws SSHException{
    Identity identity=IdentityFile.newInstance(prvkey, pubkey, this);
    addIdentity(identity, passphrase);
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   * Before registering it into identityRepository,
   * it will be deciphered with <code>passphrase</code>.
   *
   * @param name name of the identity to be used to
                 retrieve it in the identityRepository.
   * @param prvkey private key in byte array.
   * @param pubkey public key in byte array.
   * @param passphrase passphrase for <code>prvkey</code>.
   *
   */
  public void addIdentity(String name, byte[]prvkey, byte[]pubkey, byte[] passphrase) throws SSHException{
    Identity identity=IdentityFile.newInstance(name, prvkey, pubkey, this);
    addIdentity(identity, passphrase);
  }

  /**
   * Sets the private key, which will be referred in
   * the public key authentication.
   * Before registering it into identityRepository,
   * it will be deciphered with <code>passphrase</code>.
   *
   * @param identity private key.
   * @param passphrase passphrase for <code>identity</code>.
   *
   * @throws SSHException if <code>passphrase</code> is not right.
   */
  public void addIdentity(Identity identity, byte[] passphrase) throws SSHException{
    if(passphrase!=null){
      try{
        byte[] goo=new byte[passphrase.length];
        System.arraycopy(passphrase, 0, goo, 0, passphrase.length);
        passphrase=goo;
        identity.setPassphrase(passphrase);
      }
      finally{
        Util.bzero(passphrase);
      }
    }

    if(identityRepository instanceof LocalIdentityRepository){
      ((LocalIdentityRepository)identityRepository).add(identity);
    }
    else {
      // TODO
    }
  }

  /**
   * @deprecated use #removeIdentity(Identity identity)
   */
  public void removeIdentity(String name) throws SSHException{
    Vector identities = identityRepository.getIdentities();
    for(int i=0; i<identities.size(); i++){
      Identity identity=(Identity)(identities.elementAt(i));
      if(!identity.getName().equals(name))
        continue;
      identityRepository.remove(identity.getPublicKeyBlob());
      break;
    }
  }

  /**
   * Removes the identity from identityRepository.
   *
   * @param identity the indentity to be removed.
   *
   * @throws SSHException if <code>identity</code> is invalid.
   */
  public void removeIdentity(Identity identity) throws SSHException{
    identityRepository.remove(identity.getPublicKeyBlob());
  }

  /**
   * Lists names of identities included in the identityRepository.
   *
   * @return names of identities
   *
   * @throws SSHException if identityReposory has problems.
   */
  public Vector getIdentityNames() throws SSHException{
    Vector foo=new Vector();
    Vector identities = identityRepository.getIdentities();
    for(int i=0; i<identities.size(); i++){
      Identity identity=(Identity)(identities.elementAt(i));
      foo.addElement(identity.getName());
    }
    return foo;
  }

  /**
   * Removes all identities from identityRepository.
   *
   * @throws SSHException if identityReposory has problems.
   */
  public void removeAllIdentity() throws SSHException{
    identityRepository.removeAll();
  }

  /**
   * Returns the config value for the specified key.
   *
   * @param key key for the configuration.
   * @return config value
   */
  public static String getConfig(String key){
    synchronized(config){
      return (String)(config.get(key));
    }
  }

  /**
   * Sets or Overrides the configuration.
   *
   * @param newconf configurations
   */
  public static void setConfig(java.util.Hashtable newconf){
    synchronized(config){
      for(java.util.Enumeration e=newconf.keys() ; e.hasMoreElements() ;) {
	String key=(String)(e.nextElement());
	config.put(key, (String)(newconf.get(key)));
      }
    }
  }

  /**
   * Sets or Overrides the configuration.
   *
   * @param key key for the configuration
   * @param value value for the configuration
   */
  public static void setConfig(String key, String value){
    config.put(key, value);
  }

  /**
   * Sets the logger
   *
   * @param logger logger
   *
   * @see com.intellivision.util.ssh.Logger
   */
  public static void setLogger(Logger logger){
    if(logger==null) logger=DEVNULL;
    SSHConnection.logger=logger;
  }

  static Logger getLogger(){
    return logger;
  }
}
