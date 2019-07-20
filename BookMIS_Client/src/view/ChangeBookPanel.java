package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import control.BookOperator;
import model.Book;
import model.BookOperatorResult;
import tool.UITool;

public class ChangeBookPanel extends SearchPanel {
	public ChangeBookPanel(Component parentComponent, JFrame owner) {
		super("img/change.png", "修改图书");
		JButton submitButton = new JButton("修改");
		submitButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		add(submitButton, new GBC(1, 5, 4, 1).setFill(GBC.HORIZONTAL).setWeight(100, 0));
		//为修改按钮添加监听器
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						//对各字段进行非空检测
						Map<String, String> fieldMap = new LinkedHashMap<String, String>();
						fieldMap.put("书名", getBookNameField().getText().trim());
						fieldMap.put("作者", getBookAuthorField().getText().trim());
						fieldMap.put("出版社", getBookPressField().getText().trim());
						String fieldCheckResult = UITool.searchFieldCheck(fieldMap);
						//所有字段检测通过
						if(fieldCheckResult.equals("success")) {
							//对用户的输入的数据在数据库中进行检索
							try {
								BookOperator dbOperator = new BookOperator();
								Book book = new Book();
								book.setName(getBookNameField().getText().trim());
								book.setAuthor(getBookAuthorField().getText().trim());
								book.setPress(getBookPressField().getText().trim());
								
								BookOperatorResult changedResult = dbOperator.searchBook(book);
								List<Book> changeBookResult = changedResult.getResultBookList();
								if(changeBookResult.isEmpty()) {
									JOptionPane.showMessageDialog(parentComponent, "没找到您要修改的图书");
								} else {
									//将检索到的结果通过对话框显示出来，供用户选择
									JDialog changeResultDialog = new JDialog(owner, "查询结果", true);
									UITool.setCentralLocation(owner, changeResultDialog, 500, 600);
									ResultChangePanel changeResultPanel = new ResultChangePanel(changeBookResult);
									JButton changeButton = new JButton("修改");
									changeButton.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
									//为修改按钮添加监听器
									changeButton.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent event) {
											int confirmDialog = JOptionPane.showConfirmDialog(parentComponent, "确定提交修改？");
											if(confirmDialog == JOptionPane.OK_OPTION) {
												//可以一次修改提交多条数据，使用Map key的唯一特性存储主键
												try {
													Map<String, Book> changedBookMap = changeResultPanel.getChangedBookMap();
													BookOperatorResult result = dbOperator.changeBook(changedBookMap);
													changeResultDialog.dispose();
													//通过对话框显示操作结果
													if(result.getStatus()) {
														JOptionPane.showMessageDialog(parentComponent, result.getResultString());
													} else {
														List<Book> errorResultList = result.getResultBookList();
														if(errorResultList.isEmpty() || errorResultList == null) {
															JOptionPane.showMessageDialog(parentComponent, result.getResultString());
														} else {
															String noChangedBook = "";
															for(Book book : errorResultList) {
																noChangedBook += "\n" + book.toString();
															}
															JOptionPane.showMessageDialog(parentComponent, "下列图书修改失败" + noChangedBook);
														}
													}
												} catch (ConnectException e) {
													JOptionPane.showMessageDialog(changeResultDialog, "服务器连接异常，请检查服务器配置");
												} catch (InterruptedIOException e) {
													JOptionPane.showMessageDialog(changeResultDialog, "连接超时，请检查网络连接");
												} catch (IOException e) {
													e.printStackTrace();
												}
											}
										}
									});
									changeResultPanel.add(changeButton, BorderLayout.CENTER);
									JLabel tips = new JLabel("Tips: 可一次修改多条批量提交");
									tips.setFont(new Font("微软雅黑", Font.ITALIC, 17));
									changeResultDialog.add(tips, BorderLayout.NORTH);
									changeResultDialog.add(changeResultPanel);
									changeResultDialog.pack();
									changeResultDialog.setVisible(true);
								}
							} catch (ConnectException e) {
								JOptionPane.showMessageDialog(ChangeBookPanel.this, "服务器连接异常，请检查服务器配置");
							} catch (InterruptedIOException e) {
								JOptionPane.showMessageDialog(ChangeBookPanel.this, "连接超时，请检查网络连接");
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						} else {
							JOptionPane.showMessageDialog(parentComponent, "请至少输入一个条件");
						}
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();
			}
		});
	}
}
