import ReverseModule.Reverse;
import ReverseModule.ReverseHelper;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

public class ReverseServer {
  public static void main(String[] args) {
    try {
      ORB orb = ORB.init(args, null);
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("RootPOA");
      POA rootPOA = POAHelper.narrow(objRef);
      rootPOA.the_POAManager().activate();

      ReverseImpl rvr = new ReverseImpl();
      org.omg.CORBA.Object ref = rootPOA.servant_to_reference(rvr);
      Reverse h_ref = ReverseHelper.narrow(ref);

      objRef = orb.resolve_initial_references("NameService");
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      NameComponent path[] = ncRef.to_name("Reverse");
      ncRef.rebind(path, h_ref);

      System.out.println("Reverse Server reading and waiting....");
      orb.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
