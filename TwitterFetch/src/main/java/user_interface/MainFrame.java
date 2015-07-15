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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
import static java.util.concurrent.TimeUnit.*;



public class MainFrame extends JFrame{
	
	public static void main(String[]args) throws TwitterException{
		
		//User Interface (Right now only prints out all tweets for testing) 
		System.out.println("Enter a hashtag");
	    Scanner user_input = new Scanner(System.in);
	    String searchHash = user_input.next();
	    

	    
	    //Twitter Authentication
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	            .setOAuthConsumerKey("U6H9XWlxaE5CDAGpv0FpGmbTs")
	            .setOAuthConsumerSecret("4eY1oJWb0XJtW09Xw0VfLPkvu4gEcQcu0J9sxKqGDSg66Ewdpg")
	            .setOAuthAccessToken("3276463446-4mfvan1HyLSaGCxaRzXtLrmLdwrpC1jQM9WI6k3")
	            .setOAuthAccessTokenSecret("SnTYYqoexCSJ2ckXjr6D1jLdKbeL3OpF1u9ctWkQVUU6R");
	    
	    
	    //Fetch a stream of Tweets
/*	    TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	    StatusListener listener = new StatusListener(){
	    	public void onStatus(final Status status){
	    		
	    		if(status != null && status.getRetweetedStatus() != null && status.getRetweetedStatus().getUser() != null){
	    			System.out.println("@"+ status.getUser().getScreenName() + " - " + status.getText());
	    		}


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
	twitterStream.filter(fq);*/
	
    // The factory instance is re-useable and thread safe.
    final Twitter twitter = new TwitterFactory(cb.build()).getInstance();
    final Query query = new Query(searchHash);
    final ArrayList<Status> statusList = new ArrayList<Status>();
	    
    //Scheduler Setup
    ScheduledExecutorService scheduledExecutorService =
    		Executors.newScheduledThreadPool(5);
    
    ScheduledFuture scheduledFuture = 
    		scheduledExecutorService.scheduleWithFixedDelay(new Runnable(){
				private volatile boolean isRunning = true;

    			public void run(){
    				    QueryResult result;
						try {
							result = twitter.search(query);
	    					final List<Status> list = result.getTweets();
	    					if(statusList.size() == 0){
		        		        System.out.println("@" + list.get(0).getUser().getScreenName() + ":" + list.get(0).getText() + ":" + list.get(0).getCreatedAt());
	    						statusList.add(list.get(0));
	    					}
	    					else if(statusList.get(statusList.size() - 1).equals(list.get(0))){
/*	    						System.out.println("same");
		        		        System.out.println("@" + list.get(0).getUser().getScreenName() + ":" + list.get(0).getText() + ":" + list.get(0).getCreatedAt());*/
	    					} else {
		        		        System.out.println("@" + list.get(0).getUser().getScreenName() + ":" + list.get(0).getText() + ":" + list.get(0).getCreatedAt());
		        		        statusList.add(list.get(0));

	    					}
	    					
						} catch (TwitterException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    				}

    			},1L, 1L,TimeUnit.SECONDS);
	}
}
