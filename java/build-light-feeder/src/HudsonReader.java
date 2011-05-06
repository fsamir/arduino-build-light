/**
 * @author Franklin Dattein
 * @since 1.0
 */
public class HudsonReader {

  public void foo() {
    URL feedSource = new URL("http://some.rss.feed");
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));
  }
}
