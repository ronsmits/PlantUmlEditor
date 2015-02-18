package org.ronsmits.plantuml;

/**
 * Created by ron on 17-2-15.
 */
interface TextListener {
    /**
     * Called when the text that contains the plantuml language is updated and the drawing needs to be made again
     *
     * @param text that is changed
     */
    void textUpdated(String text);
}
