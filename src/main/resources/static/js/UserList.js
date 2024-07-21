 
		function sort(column) {
			let sortDirection = document.getElementById('sortDirection').value;
			sortDirection = sortDirection === 'asc' ? 'desc' : 'asc';
			document.getElementById('sortDirection').value = sortDirection;

			// Update sort icon based on sort direction and column
			const sortIconId = document.getElementById('sortIconId');
			const sortIconUsername = document
					.getElementById('sortIconUsername');
			if (column === 'id') {
				sortIconId.innerHTML = sortDirection === 'asc' ? '▲' : '▼';
				sortIconUsername.innerHTML = '';
			} else if (column === 'username') {
				sortIconUsername.innerHTML = sortDirection === 'asc' ? '▲'
						: '▼';
				sortIconId.innerHTML = '';
			}

			window.location.href = `/admin/get-all-users?page=0&size=10&sortColumn=${column}&sortDirection=${sortDirection}`;
		}
		
		
		function downloadPdf() {
            var iframe = document.createElement('iframe');
            iframe.style.display = 'none';
            iframe.src = '/admin/get-all-users';
            document.body.appendChild(iframe);

            iframe.onload = function() {
                alert('PDF file is downloading.....');
                document.body.removeChild(iframe);
            };
        }
        
        function downloadCsv() {
            var iframe = document.createElement('iframe');
            iframe.style.display = 'none';
            iframe.src = '/admin/get-all-users';
            document.body.appendChild(iframe);

            iframe.onload = function() {
                alert('CSV file is downloading.....');
                document.body.removeChild(iframe);
            };
        }
		 