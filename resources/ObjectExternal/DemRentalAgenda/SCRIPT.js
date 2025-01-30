var DemRentalAgenda = DemRentalAgenda || (() => {

const debug = true;
let rental; 

function render(url) {
	$ui.loadCalendar(function() {
		$ui.getUIObject('DemRental', 'agenda_DemoRental', function(o) {
			rental = o;
			rental.getMetaData(calendar);
		});
	});
}

function calendar() {
	new FullCalendar.Calendar($("#rentalcalendar")[0],{
		headerToolbar: {
			start: "prev,next today",
			center: "title",
			end: 'dayGridMonth,timeGridWeek,dayGridDay,listWeek'
		},
		nowIndicator: true,
		timezone: $ui.grant.timezone || 'local',
		locale: $ui.grant.langiso || 'en',
		initialView: 'timeGridWeek',
		editable: true,
		firstDay: 1,
		slotMinTime: '08:00:00',
		slotMaxTime: '20:00:00',
		businessHours: {
				daysOfWeek: [ 1, 2, 3, 4, 5 ],
				startTime: '09:00',
				endTime: '18:00'
		},
		eventClick: function(info) {
			const id = info.event.id;
			if (debug) $app.info("Rental " + id + " clicked");
			$ui.displayForm(null, "DemRental", id, { nav: "add" });
		},
		eventDrop: function(info) {
			const s = moment(info.event.start).utc().format('YYYY-MM-DD HH:mm:ss');
			let d = info.event.extendedProps.data;
			if (debug) $app.info('Rental ' + info.event.id + ' dropped to ' + s);
			d.demRenStartDate = s;
			rental.update(function() {
				d = rental.item;
				if (debug) $app.info("Rental " + d.demReqReference + " rental date updated to " + s);
			}, d);
		},
		events: function(info, success, failure) {
			const start = moment(info.start);
			const end = moment(info.end);
			const f = "YYYY-MM-DD HH:mm:ss Z";
			const dmin = start.format(f);
			const dmax = end.format(f);
			if (debug) $app.info("Calendar view range = " + dmin + " to " + dmax);
			rental.search(function() {
				if (debug) $app.info(rental.list.length + " rentals found between " + dmin + " and " + dmax);
				const evts = [];
				for (const item of rental.list) {
					if (item.demRenStartDate !== '') { // ZZZ When using intervals empty values are included !
						const s = moment(item.demRenStartDate);
						const e = moment(item.demRenEndDate);
						evts.push({
							id: item.row_id,
							data: item,
							title: item.demReqReference + ": " + item.demReqTitle,
							start: s.toDate(),
							end: e.toDate(), 
							editable: true,	
							durationEditable: false,
							color: item.demReqStatus == "PENDING" ? "orange" : (item.demReqStatus == "VALIDATED" ? "green" : (item.demReqStatus == "REJECTED" ? "red" : "gray")),
							borderColor: "lightgray",
							textColor: "white"
						});
					}
				}
				if (debug) $app.info(evts.length + " rental displayed between " + dmin + " and " + dmax);
				success(evts);
			}, { demRenStartDate: dmin + ";" + dmax, demReqStatus:"PENDING;VALIDATED;REJECTED"}, { inlineDocs: false });
		}
	}).render();
}
return { render: render };
})();
