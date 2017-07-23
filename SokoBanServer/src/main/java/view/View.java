package view;

import java.util.Observer;

/**
 * <p> view facade - part of MVVM architecture </p>
 * @author Elad Ben Zaken
 *
 */
public interface View extends Observer{
	public void close();
}
