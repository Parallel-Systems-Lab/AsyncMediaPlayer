package editor;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import player.player;

public class maskEditor {

	private JFrame frame;
	private JTextField pathField;
	OutputStream out;
	InputStream input;
	SyncPoints pointsDB = new SyncPoints();
	JScrollPane listScroller;
	Process proc;
	DefaultListModel<String> listModel;
	int currentSelectedIndex = 0;
	int length = 0;
	
	boolean isPlaying = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						maskEditor window = new maskEditor();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public maskEditor() throws IOException {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		final int margenBetweenButtons = 0;
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		int listEmptyMinSize = 250;

		ImageIcon buttonIcon;
		int currentX = 0;
		int currentY = 0;

		// begin button
		buttonIcon = getIcons.getBegginningButton();

		JButton btnBeginButton = new JButton(buttonIcon);
		btnBeginButton.setBorder(BorderFactory.createEmptyBorder());
		btnBeginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String timeString = getTime();
				String[] times = timeString.split("\r\n");

				int time = Integer.parseInt(times[0]);
				
				for (int i=pointsDB.points.size()-1; i>=0 ; i--)
				{
					data d = pointsDB.points.get(i);
					if (d.startInSeconds < time)
					{
						seek(d.startInSeconds);
						return;
					}
				}
			}
		});
		btnBeginButton.setBounds(currentX, 0, buttonIcon.getIconWidth(),
				buttonIcon.getIconHeight());
		frame.getContentPane().add(btnBeginButton);

		currentX += buttonIcon.getIconHeight() + margenBetweenButtons;
		currentY += buttonIcon.getIconWidth() + margenBetweenButtons;

		// rewind button
		buttonIcon = getIcons.getFastRewindButton();

		JButton btnRewindButton = new JButton(buttonIcon);
		btnRewindButton.setBorder(BorderFactory.createEmptyBorder());
		btnRewindButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					out.write("rewind\n".getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					out.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRewindButton.setBounds(currentX, 0, buttonIcon.getIconWidth(),
				buttonIcon.getIconHeight());
		frame.getContentPane().add(btnRewindButton);

		currentX += buttonIcon.getIconHeight() + margenBetweenButtons;

		// play button
		buttonIcon = getIcons.getPlayButton();

		JButton btnPlayButton = new JButton(getIcons.getPlayButton());
		btnPlayButton.setBorder(BorderFactory.createEmptyBorder());
		btnPlayButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					out.write("play\n".getBytes());
					out.flush();
				} catch (Exception e1) {
					// @TODO
				}
				
				isPlaying = !isPlaying;
			}
		});
		btnPlayButton.setBounds(currentX, 0, buttonIcon.getIconWidth(),
				buttonIcon.getIconHeight());
		frame.getContentPane().add(btnPlayButton);

		currentX += buttonIcon.getIconHeight() + margenBetweenButtons;
		// pause button
		buttonIcon = getIcons.getPauseButton();

		JButton btnPauseButton = new JButton(buttonIcon);
		btnPauseButton.setBorder(BorderFactory.createEmptyBorder());
		btnPauseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					out.write("pause\n".getBytes());
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					out.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				isPlaying = !isPlaying;
			}
		});
		btnPauseButton.setBounds(currentX, 0, buttonIcon.getIconWidth(),
				buttonIcon.getIconHeight());
		frame.getContentPane().add(btnPauseButton);

		currentX += buttonIcon.getIconHeight() + margenBetweenButtons;

		// stop button
		buttonIcon = getIcons.getStopButton();

		JButton btnStopButton = new JButton(buttonIcon);
		btnStopButton.setBorder(BorderFactory.createEmptyBorder());
		btnStopButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					out.write("stop\n".getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					out.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				isPlaying = false;
			}
		});
		btnStopButton.setBounds(currentX, 0, buttonIcon.getIconWidth(),
				buttonIcon.getIconHeight());
		frame.getContentPane().add(btnStopButton);

		currentX += buttonIcon.getIconHeight() + margenBetweenButtons;

		// fast forward button
		buttonIcon = getIcons.getFastForwardButton();

		JButton fastForwardButton = new JButton(buttonIcon);
		fastForwardButton.setBorder(BorderFactory.createEmptyBorder());
		fastForwardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					out.write("fastforward\n".getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					out.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		fastForwardButton.setBounds(currentX, 0, buttonIcon.getIconWidth(),
				buttonIcon.getIconHeight());
		frame.getContentPane().add(fastForwardButton);

		currentX += buttonIcon.getIconHeight() + margenBetweenButtons;

		// end button
		buttonIcon = getIcons.getEndButton();

		JButton endButton = new JButton(buttonIcon);
		endButton.setBorder(BorderFactory.createEmptyBorder());
		endButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String timeString = getTime();
				String[] times = timeString.split("\r\n");

				int time = Integer.parseInt(times[0]);
				
				for (data d : pointsDB.points)
				{
					if (d.startInSeconds > time)
					{
						seek(d.startInSeconds);
						return;
					}
				}
			}
		});
		endButton.setBounds(currentX, 0, buttonIcon.getIconWidth(),
				buttonIcon.getIconHeight());
		frame.getContentPane().add(endButton);

		currentX += buttonIcon.getIconHeight() + margenBetweenButtons;

		// show points places
		listModel = new DefaultListModel<String>();

		AddPointsToListModel(listModel);
		JList<String> list = new JList<String>(listModel); // data has type
															// Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		// ...
		ListSelectionListener listSelectionListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				currentSelectedIndex = listSelectionEvent.getFirstIndex();
			}
		};
		list.addListSelectionListener(listSelectionListener);

		listScroller = new JScrollPane(list);

		listScroller.setBounds(0, currentY, currentX, listModel.getSize() * 20
				+ margenBetweenButtons + listEmptyMinSize);

		currentY += listModel.getSize() * 20 + margenBetweenButtons
				+ listEmptyMinSize;

		// listScroller.setPreferredSize(new Dimension(250, 80));
		frame.getContentPane().add(listScroller);

		final maskEditor me = this;

		// add button
		buttonIcon = getIcons.getAddButton();

		JButton addButton = new JButton(buttonIcon);
		addButton.setBorder(BorderFactory.createEmptyBorder());
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// int time = Integer(getTime());
				String timeString = getTime();
				String[] times = timeString.split("\r\n");

				int time = Integer.parseInt(times[0]);
				
				data temp = null;
				if (pointsDB.getPoints().size() > 0)
				{
					temp = pointsDB.getPoints().get(pointsDB.getPoints().size()-1);
				}
				
				popUpData window = new popUpData(time, me, temp);
				window.frame.setVisible(true);

				// pause current playing
				try {
					if (isPlaying) {
						out.write("pause\n".getBytes());
						isPlaying = false;
					}
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					out.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		addButton.setBounds(currentX / 2 - buttonIcon.getIconWidth()
				, currentY, buttonIcon.getIconWidth(),
				buttonIcon.getIconHeight());
		frame.getContentPane().add(addButton);

		// currentX += buttonIcon.getIconHeight()+margenBetweenButtons;

		// remove button
		buttonIcon = getIcons.getRemoveButton();

		JButton removeButton = new JButton(buttonIcon);
		removeButton.setBorder(BorderFactory.createEmptyBorder());
		removeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pointsDB.removePoint(pointsDB.getPoints().get(
						currentSelectedIndex));
				AddPointsToListModel(listModel);
				listScroller.repaint();
			}
		});
		removeButton.setBounds(currentX / 2 , currentY,
				buttonIcon.getIconWidth(), buttonIcon.getIconHeight());
		frame.getContentPane().add(removeButton);

		final maskEditor weakSelf = this;
		// done button
		JButton doneButton = new JButton("Done");
		doneButton.setBorder(BorderFactory.createEmptyBorder());
		doneButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// make sure to stop inner player
//				try {
//					out.write("pause\n".getBytes());
//				} catch (IOException e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
				
				if (pointsDB.getPoints().size() >0)
				{
					new MaskCreator(pointsDB.getPoints());
				}
				try {
//					player pl = new player(pathField.getText()); //amawasi
					player pl = new player(pathField.getText(), SyncPoints.points);
					pl.frame.setVisible(true);
					pl.maskeditor = weakSelf;
					frame.setVisible(false);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		doneButton.setBounds(currentX / 2 + buttonIcon.getIconWidth(), currentY,
				buttonIcon.getIconWidth(), buttonIcon.getIconHeight());
		frame.getContentPane().add(doneButton);
		
		currentY += buttonIcon.getIconHeight() + margenBetweenButtons;


		// currentX += buttonIcon.getIconHeight()+margenBetweenButtons;

		// path browser
		this.pathField = new JTextField();
		pathField.setBounds(0, currentY, currentX - margenBetweenButtons - 90,
				20);
		JButton browse = new JButton("Browse");
		browse.setBounds(currentX - margenBetweenButtons - 85, currentY, 90, 20);
		currentY += 20 + margenBetweenButtons;
		browse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Create a file chooser
				final JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(frame);
				try {
					pathField.setText(fc.getSelectedFile().getAbsolutePath());
					String[] arguments = new String[] { "vlc", "-I rc" };
					proc = new ProcessBuilder(arguments).start();
					input = proc.getInputStream();
					out = proc.getOutputStream();
					out.write(("enqueue "
							+ fc.getSelectedFile().getAbsolutePath() + "\n")
							.getBytes());
					out.flush();

				} catch (Exception eee) {
					// pathField.setText("");
				}

			}
		});

		frame.getContentPane().add(pathField);
		frame.getContentPane().add(browse);

		// update time
		final JLabel time = new JLabel();

		currentY += margenBetweenButtons;

		time.setBounds(0, currentY, currentX - margenBetweenButtons + 10, 10);
		time.setText("Time.");

		currentY += 10;

		Thread updateTimeThread = new Thread() {
			@Override
			public void run() {
				
				while (true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					}
					try {
						time.setText(getTime());
						if(length == 0)
						{
							length = getLength();
						}
					} catch (Exception e) {

					}
				}
			};
		};

		updateTimeThread.start();

		frame.getContentPane().add(time);


		// update frame
		frame.setBounds(100, 100, currentX - margenBetweenButtons + 10,
				currentY + 30);

	}
	
	private int getLength()
	{
		try {
			byte[] timeBytes = new byte[9999];
			out.write("get_length\n".getBytes());
			out.flush();

			input.read(timeBytes);
			String time = new String(timeBytes);

			String[] times = time.split("\r\n");

			System.out.println("Lenght is " + Integer.parseInt(times[0]));
			
			return Integer.parseInt(times[0]);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;

	}

	private String getTime() {
		try {
			byte[] timeBytes = new byte[9999];
			out.write("get_time\n".getBytes());
			out.flush();

			input.read(timeBytes);
			String time = new String(timeBytes);

			return time;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void seek(int seconds) {
		try {
			out.write(("seek "+seconds+"\n").getBytes());
			out.flush();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	private void AddPointsToListModel(DefaultListModel<String> listModel) {
		List<data> listPoints = pointsDB.getPoints();
		Collections.sort(pointsDB.getPoints());
		listModel.clear();
		for (int i = 0; i < listPoints.size(); i++) {

			data d = listPoints.get(i);

			String type = "";
			switch (d.type) {
			case SyncTypeDontWait:
				type = "Dont Wait.";
				break;
			case SyncTypeWait:
				type = "Wait for each other.";
				break;
			case SyncTypeDT:
				type = "Follow in dt "+d.dt+".";
			default:
				assert (false);
				break;
			}

			listModel
					.addElement("At:" + d.startInSeconds + ". video:"
							+ d.videoFunc + ". audio:" + d.audioFunc
							+ ". sync:" + type);
		}
	}

	public void finishAdding(data d) {
		pointsDB.getPoints().add(d);
		AddPointsToListModel(listModel);
		listScroller.repaint();
	}

	public void finishRemoving(data d) {
		pointsDB.removePoint(d);
		AddPointsToListModel(listModel);
		listScroller.repaint();
	}
	
	public void reOpen()
	{
		frame.setVisible(true);
	}
}
