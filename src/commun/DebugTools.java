package commun;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class DebugTools {
	public static void d(String msg) {
		System.out.println(msg+"\n");
	}
	
	public static void d(Registry r) {
		int i;
		try {
			d("list length : "+Integer.toString(r.list().length));
		} catch (AccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			for (i=0;i<r.list().length;i++) {
				d("print index "+Integer.toString(i));
				d(r.list()[i]);
			}
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
