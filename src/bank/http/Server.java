package bank.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.Bank;
import bank.Bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

@WebServlet("/bank")
public class Server extends HttpServlet {

	static ServerHelper helper;
		static{
		helper = new ServerHelper();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
	
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		
		Enumeration<?> e = request.getParameterNames();

		String name = (String) e.nextElement();
		String method = request.getParameter(name);
		String[] params = new String[3];
		int i = 0;
		while (e.hasMoreElements()) {
			name = (String) e.nextElement();
			params[i] = request.getParameter(name);
			i++;
		}

		out.writeObject(helper.getResponse(method, params));
		
		byte[] buf = byteOut.toByteArray();
		response.setContentType("application/octet-stream");
		response.setContentLength(buf.length);
		
		ServletOutputStream sOut = response.getOutputStream();
		sOut.write(buf);
		sOut.close();
		
	}
	
	static class ServerHelper{
		
		static Bank bank;
		static {
			bank = new Bank();
		}

		private Object getResponse(String method, String[] params) throws IOException {
			
			switch (method) {
			case "getAccountNumbers":
				System.out.println("getAccountNumbers");
				return bank.getAccountNumbers();
			case "createAccount":
				System.out.println("createAccount");
				return bank.createAccount(params[0]);
			case "closeAccount":
				System.out.println("closeAccount");
				return bank.closeAccount(params[0]);
			case "getAccount":
				System.out.println("getAccount");
				Account acc = (Account) bank.getAccount(params[0]);
				if (acc != null) {
					return acc.getOwner();
				} else {
					return null;
				}
			case "transfer":
				System.out.println("transfer");
				try {
					bank.transfer(bank.getAccount(params[0]),
							bank.getAccount(params[1]),
							Double.parseDouble(params[2]));
					return null;
				} catch (Exception e) {
					return e;
				}
			case "getBalance":
				System.out.println("getBalance");
				try {
					double balance = bank.getAccount(params[0]).getBalance();
					return balance;
				} catch (IllegalArgumentException e) {
					return e;
				}
			case "isActive":
				System.out.println("isActive");
				try {
					boolean isActive = bank.getAccount(params[0]).isActive();
					return isActive;
				} catch (NullPointerException e) {
					return e;
				}
			case "deposit":
				System.out.println("deposit");
				try {
					bank.getAccount(params[0]).deposit(
							Double.parseDouble(params[1]));
					return null;
				} catch (Exception e) {
					return e;
				}
			case "withdraw":
				System.out.println("withdraw");
				try {
					bank.getAccount(params[0]).withdraw(
							Double.parseDouble(params[1]));
					return null;
				} catch (Exception e) {
					return e;
				}
			default:
				System.out.println("False input");
				throw new RuntimeException();

			}
		}
	}
}
