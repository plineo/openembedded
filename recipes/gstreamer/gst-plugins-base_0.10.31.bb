require gst-plugins.inc

SRC_URI_append_opendreambox = " file://samihack.patch;patch=1"
SRC_URI_append_vuplus = " file://samihack.patch;patch=1"

PR = "${INC_PR}.0"

PROVIDES += "gst-plugins"

# gst-plugins-base only builds the alsa plugin
# if alsa has been built and is present.  You will
# not get an error if this is not present, just 
# a missing alsa plugin
#DEPENDS += "${@base_contains('DISTRO', 'opendreambox', 'alsa-lib', 'cdparanoia pango libtheora alsa-lib libsm virtual/libx11 freetype gnome-vfs libxv', d)}"
DEPENDS += "${@base_contains('DISTRO', 'vuplus', 'alsa-lib', 'cdparanoia pango libtheora alsa-lib libsm virtual/libx11 freetype gnome-vfs libxv', d)}"
EXTRA_OECONF_opendreambox += "--disable-theora --disable-pango --with-audioresample-format=int"
EXTRA_OECONF_vuplus += "--disable-theora --disable-pango --with-audioresample-format=int"

