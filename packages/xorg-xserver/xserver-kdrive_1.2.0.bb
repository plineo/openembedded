require xserver-kdrive-common.inc

DEPENDS += "libxkbfile libxcalibrate"

PE = "1"
PR = "r9"

SRC_URI = "${XORG_MIRROR}/individual/xserver/xorg-server-${PV}.tar.bz2 \
	${KDRIVE_COMMON_PATCHES} \
	file://enable-xcalibrate.patch;patch=1 \
        file://w100.patch;patch=1 \
        file://w100-fix-offscreen-bmp.patch;patch=1 \
        file://fbcompositesrc8888revnpx0565.patch;patch=1 \
        file://xcalibrate_coords.patch;patch=1 \
	"
       
S = "${WORKDIR}/xorg-server-${PV}"

W100_OECONF = "--disable-w100"
W100_OECONF_arm = "--enable-w100"
