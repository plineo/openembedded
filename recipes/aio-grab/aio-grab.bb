DESCRIPTION="AiO screen grabber for dreambox stbs"
LICENSE = "GPL"

PR = "r0"
PV = "0.8+cvs${SRCDATE}"
SRCDATE = "20101123"

SRC_URI="cvs://anonymous@cvs.schwerkraft.elitedvb.net/cvsroot/aio-grab;module=aio-grab;method=pserver"
SRC_URI_append_vuplus = "\
	file://aio-grab_vuplus.patch;patch=1"

S = "${WORKDIR}/aio-grab"

inherit autotools pkgconfig
