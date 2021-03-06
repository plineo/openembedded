require gst-plugins.inc

SRC_URI += "file://flvdemux-ecma.diff;patch=1 \
	file://qtdemux-fix-partial-parsing-of-ctts-table.patch;patch=1"

PR = "${INC_PR}.1"

DEPENDS += "gst-plugins-base"

#inherit gconf
#DEPENDS += "hal pulseaudio speex libsoup-2.4 flac gst-plugins-base openssl popt esound"
#PACKAGES =+ "gst-plugin-gconfelements"
#FILES_gst-plugin-gconfelements += "${sysconfdir}/gconf"

do_configure_prepend() {
	sed -i -e s:docs::g Makefile.am
}
