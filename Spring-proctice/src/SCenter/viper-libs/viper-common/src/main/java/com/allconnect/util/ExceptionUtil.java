package com.A.util;

import java.sql.SQLException;

public class ExceptionUtil
{
	/**
	 * Helper method to find the root cause exception and then use it to add
	 * proper message in the status.
	 * 
	 * @param th
	 * @return
	 */
	public static Throwable unwindException(Throwable th) {
		if (th instanceof SQLException) {
			SQLException sql = (SQLException) th;
			if (sql.getNextException() != null) {
				return unwindException(sql.getNextException());
			}
		}else if (th.getCause() != null) {
			return unwindException(th.getCause());
		}
		return th;
	}

}
