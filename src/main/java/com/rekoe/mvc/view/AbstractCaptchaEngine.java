package com.rekoe.mvc.view;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.engine.CaptchaEngineException;
import com.octo.captcha.engine.image.ImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptcha;
import com.octo.captcha.image.ImageCaptchaFactory;

public abstract class AbstractCaptchaEngine extends ImageCaptchaEngine {
	private List<CaptchaFactory> factories = new ArrayList<CaptchaFactory>();
	private Random myRandom = new SecureRandom();

	public AbstractCaptchaEngine(String path) {
		buildInitialFactories(path);
		checkFactoriesSize();
	}

	protected abstract void buildInitialFactories(String path);

	public boolean addFactory(ImageCaptchaFactory factory) {
		return this.factories.add(factory);
	}

	public void addFactories(ImageCaptchaFactory[] factories) {
		for (int i = 0; i < factories.length; i++)
			this.factories.add(factories[i]);
	}

	public CaptchaFactory[] getFactories() {
		return (CaptchaFactory[]) (CaptchaFactory[]) this.factories.toArray(new CaptchaFactory[this.factories.size()]);
	}

	public void setFactories(CaptchaFactory[] factories) throws CaptchaEngineException {
		if ((factories == null) || (factories.length == 0)) {
			throw new CaptchaEngineException("impossible to set null or empty factories");
		}
		List<CaptchaFactory> tempFactories = new ArrayList<CaptchaFactory>();

		for (int i = 0; i < factories.length; i++) {
			if (ImageCaptchaFactory.class.isAssignableFrom(factories[i].getClass())) {
				throw new CaptchaEngineException("This factory is not an image captcha factory " + factories[i].getClass());
			}
			tempFactories.add(factories[i]);
		}

		this.factories = tempFactories;
	}

	public ImageCaptchaFactory getImageCaptchaFactory() {
		return (ImageCaptchaFactory) this.factories.get(this.myRandom.nextInt(this.factories.size()));
	}

	@Override
	public ImageCaptcha getNextImageCaptcha() {
		return getImageCaptchaFactory().getImageCaptcha();
	}

	@Override
	public ImageCaptcha getNextImageCaptcha(Locale locale) {
		return getImageCaptchaFactory().getImageCaptcha(locale);
	}

	private void checkFactoriesSize() {
		if (this.factories.size() == 0)
			throw new CaptchaException("This gimpy has no factories. Please initialize it properly with the buildInitialFactory() called by the constructor or the addFactory() mehtod later!");
	}

}