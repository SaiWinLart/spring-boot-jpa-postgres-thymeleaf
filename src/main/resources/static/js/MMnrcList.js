  function selectAllCheckboxes(source) {
            checkboxes = document.getElementsByName('mmnrcIds');
            for(var i=0, n=checkboxes.length;i<n;i++) {
                checkboxes[i].checked = source.checked;
            }
        }
        
           function validateForm() {
            var checkboxes = document.getElementsByName('mmnrcIds');
            var isChecked = false;
            for (var i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].checked) {
                    isChecked = true;
                    break;
                }
            }
            if (!isChecked) {
                alert('Please select at least one mmnrc Id to delete.');
                return false;
            }
            return true;
        }