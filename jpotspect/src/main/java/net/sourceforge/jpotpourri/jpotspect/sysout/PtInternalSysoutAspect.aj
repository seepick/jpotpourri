package net.sourceforge.jpotpourri.jpotspect.sysout;


public aspect PtInternalSysoutAspect extends PtAbstractSysoutAspect {

	pointcut withinLibrary() :
		within(net.sourceforge.jpotpourri.**..*) &&
		!sysoutExceptions();
	
	pointcut sysoutExceptions() :
		within(net.sourceforge.jpotpourri.**..PtDemo);
	
}
