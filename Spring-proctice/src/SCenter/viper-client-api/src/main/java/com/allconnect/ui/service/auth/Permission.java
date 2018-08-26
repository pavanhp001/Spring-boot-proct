package com.A.ui.service.auth;

import java.util.LinkedList;
import java.util.List;

public enum Permission {

	none(-1),   execute(0), mask(1), view(2), list(3), browse(4), create(5), append(6), edit(
			7), delete(8), ownership(9),   full(10);

	// Order of insertion is important. Most aggressive permission first.
	private static List<Permission> libraryPermissions = new LinkedList<Permission>();
	static {
		libraryPermissions.add(full);
		libraryPermissions.add(ownership);
		libraryPermissions.add(delete);
		libraryPermissions.add(edit);
		libraryPermissions.add(append);
		libraryPermissions.add(create);
		libraryPermissions.add(browse);
		libraryPermissions.add(list);
		libraryPermissions.add(view); 
		libraryPermissions.add(mask);
		libraryPermissions.add(execute);
	}

	private int level;

	private Permission(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public static Permission level(List<String> currentPermissions) {
		Permission level = Permission.none;

		if ((currentPermissions == null) || (currentPermissions.size() == 0)) {
			return level;
		}

		for (Permission libraryPermission : libraryPermissions) {

			for (String currentP : currentPermissions) {

				if (libraryPermission.name().equalsIgnoreCase(currentP)
						|| (currentP.indexOf(libraryPermission.name()) != -1)) {
					return libraryPermission;
				}
			}

		}

		return level;
	}
	
	public static int getMaxLevel(List<String> permissions){
		int maxLevel = -1;
		if(permissions != null){
			for (String currentP : permissions) {
				Permission p = Permission.valueOf(currentP.toLowerCase());
				int level = p.getLevel();
				if(level > maxLevel){
					maxLevel = level;
				}
			}
		}
		return maxLevel;
	}

}
