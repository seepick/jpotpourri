package net.sourceforge.jpotpourri.jpotspect.sysout;


public abstract aspect PtAbstractSysoutAspect {

	abstract pointcut withinLibrary();

	pointcut invalidSysoutCall() :
		get(* java.lang.System.out) &&
		withinLibrary() &&
		!within(@net.sourceforge.jpotpourri.jpotspect.sysout.PtMaySysout *);

	declare error: invalidSysoutCall(): "Printing messages to System.out is prohibited!";

}
