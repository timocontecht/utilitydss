package org.visico.utilitydss.server.processsim;
import desmoj.core.advancedModellingFeatures.Res;
import desmoj.core.simulator.Model;

/** class to store resources that are only needed up to a 
 * certain point during the simulation experiment. At this point 
 * the function stopUse can be called and the resource cannot be used any
 * longer. It returns the average use value for the time it was stopped.
 * @author timo
 *
 */
public class PartTimeRes extends Res 
{

	public PartTimeRes(Model arg0, String arg1, int arg2, boolean arg3,
			boolean arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
		// TODO Auto-generated constructor stub
	}

	/** function returns the average usage of this resource: In case the resource 
	 * is no longer required, it returns the value at the time it stopped being used, 
	 * otherwise the normal value of the super Resource class.  
	 */
	public double avgUsage()
	{
		if (stopped)
			return avgUsageStop;
		else
			return super.avgUsage();
	}
	
	public void stopUse()
	{
		avgUsageStop = super.avgUsage();
		stopped = true;
	}
	
	public boolean provide(int n)
	{
		if (stopped)
			return false;
		else
			return super.provide(n);
	}
	
	double avgUsageStop;
	boolean stopped;
}
