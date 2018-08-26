package com.A.util.fileingest;

public class PGPConfig {

	private String privateKeyFilePath;
	private String privateKeyFileName;
	private String publicKeyFilePath;
	private String publicKeyFileName;
	private String passphrase;
	private String privateKey;
	private String publicKey;
	
	
	public String getPrivateKeyFilePath() {
		return privateKeyFilePath;
	}
	public void setPrivateKeyFilePath(String privateKeyFilePath) {
		this.privateKeyFilePath = privateKeyFilePath;
	}
	public String getPrivateKeyFileName() {
		return privateKeyFileName;
	}
	public void setPrivateKeyFileName(String privateKeyFileName) {
		this.privateKeyFileName = privateKeyFileName;
	}
	public String getPublicKeyFilePath() {
		return publicKeyFilePath;
	}
	public void setPublicKeyFilePath(String publicKeyFilePath) {
		this.publicKeyFilePath = publicKeyFilePath;
	}
	public String getPublicKeyFileName() {
		return publicKeyFileName;
	}
	public void setPublicKeyFileName(String publicKeyFileName) {
		this.publicKeyFileName = publicKeyFileName;
	}
	public String getPassphrase() {
		return passphrase;
	}
	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	
}
