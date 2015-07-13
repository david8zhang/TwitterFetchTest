package user_interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterBase;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;




public class MainFrame extends JFrame{
	
	public static void main(String[]args) throws TwitterException{
		//User Interface
		System.out.println("Enter a hashtag");
	    Scanner user_input = new Scanner(System.in);
	    String searchHash = user_input.next();
	    
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	            .setOAuthConsumerKey("U6H9XWlxaE5CDAGpv0FpGmbTs")
	            .setOAuthConsumerSecret("4eY1oJWb0XJtW09Xw0VfLPkvu4gEcQcu0J9sxKqGDSg66Ewdpg")
	            .setOAuthAccessToken("3276463446-4mfvan1HyLSaGCxaRzXtLrmLdwrpC1jQM9WI6k3")
	            .setOAuthAccessTokenSecret("SnTYYqoexCSJ2ckXjr6D1jLdKbeL3OpF1u9ctWkQVUU6R");
	    
	    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	    StatusListener listener = new StatusListener(){
	    	public void onStatus(Status status){
	    		if(status != null && status.getRetweetedStatus() != null && status.getRetweetedStatus().getUser() != null)
	    			System.out.println("@"+ status.getUser().getScreenName() + " - " + status.getText());
	    	}

			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				arg0.printStackTrace();
				
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// TODO Auto-generated method stub
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());

				
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				// TODO Auto-generated method stub
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);

				
			}

			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
                System.out.println("Got track limitation notice:" + arg0);
				
			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
                System.out.println("Got stall warning:" + arg0);

				
			}
	};
	String keywords[] = {searchHash};
	FilterQuery fq = new FilterQuery(keywords);
	twitterStream.addListener(listener);
	twitterStream.filter(fq);
	

	}
}
