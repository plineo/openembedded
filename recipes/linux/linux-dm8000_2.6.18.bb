require linux-opendreambox-2.6.18.inc

PR="${PR_INC}.0"

SRC_URI += "\
	file://linux-2.6.18-disable-unneeded-uarts.patch;patch=1 \
"
